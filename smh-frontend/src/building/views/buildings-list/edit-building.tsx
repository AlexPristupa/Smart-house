import { useCallback } from 'react';
import { Form } from 'antd';
import { BuildingForm } from 'building/components';
import { IBuilding, IBuildingObject } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useEditBuilding, useGetBuildingData } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useTranslation } from 'react-i18next';
import { errorHandler } from 'services';

export const EditBuilding: React.FC = () => {
  const queryClient = useQueryClient();
  const {
    params: { buildingId },
  } = useRouter();

  const { t } = useTranslation();

  const { data, isLoading: isDocLoading } = useGetBuildingData(buildingId!);

  const [form] = Form.useForm<IBuilding>();

  const { goToBuilding, goToBuildingsList } = useRouter();

  const {
    mutate,
    isLoading: isSubmiting,
    error,
  } = useEditBuilding({
    onMutate: async editBuilding => {
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.BUILDING, buildingId]);

      const previousBuilding = queryClient.getQueryData([BUILDING_QUERY_KEYS.BUILDING, buildingId]);

      queryClient.setQueryData([BUILDING_QUERY_KEYS.BUILDING, buildingId], editBuilding);

      return { previousBuilding };
    },
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.BUILDING, buildingId]);
      form.resetFields();
      goToBuildingsList();
      console.log('queryClient', queryClient);
    },
    onSettled: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.BUILDING, buildingId]);
    },
    onError: (err, variables, context: any) => {
      errorHandler(err, form);
      if (context?.previousBuilding) {
        const previousBuilding = context?.previousBuilding as unknown as IBuildingObject;
        queryClient.setQueryData<IBuildingObject>(BUILDING_QUERY_KEYS.BUILDING_LIST, previousBuilding);
      }
    },
  });

  const onCancel = useCallback(() => {
    goToBuilding();
  }, [goToBuilding]);

  const onFinish = useCallback(
    (values: IBuildingObject) => {
      const facilitesObject = {
        ...values,
        entityId: buildingId,
        action: 'edit',
        editImage: data?.images && JSON.stringify(data?.images) !== JSON.stringify(values?.images),
        editPhoto: values.photo && JSON.stringify(data?.photo) !== JSON.stringify(values?.photo),
      };
      mutate(facilitesObject);
    },
    [buildingId, data?.images, data?.photo, mutate],
  );

  const isLoading = isDocLoading || isSubmiting;

  if (data) {
    return (
      <BuildingForm
        form={form}
        isLoading={isLoading}
        initialValues={data}
        onCancel={onCancel}
        onFinish={onFinish}
        title={t('building.form.title.editBuilding')}
        error={error ? error : undefined}
      />
    );
  }

  return null;
};
