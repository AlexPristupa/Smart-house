import { useCallback } from 'react';
import { Form } from 'antd';
import { BuildingForm } from 'building/components';
import { IBuilding, IBuildingObject } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useAddBuilding } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useTranslation } from 'react-i18next';
import { errorHandler } from 'services';

export const AddBuilding: React.FC = () => {
  const [form] = Form.useForm<IBuilding>();
  const { t } = useTranslation();
  const { goToBuildingsList } = useRouter();

  const queryClient = useQueryClient();

  const { mutate, isLoading } = useAddBuilding({
    onMutate: () => {
      queryClient.cancelQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
      form.resetFields();
      goToBuildingsList();
    },
    onError: err => {
      errorHandler(err, form);
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
    },
    onSettled: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
    },
  });

  const onCancel = useCallback(() => {
    goToBuildingsList();
  }, [goToBuildingsList]);

  const onFinish = useCallback(
    (values: IBuildingObject) => {
      const facilitesObject = {
        ...values,
        action: 'new',
        editImage: true,
        editPhoto: true,
      };
      mutate(facilitesObject);
    },
    [mutate],
  );

  return (
    <BuildingForm form={form} isLoading={isLoading} onCancel={onCancel} onFinish={onFinish} title={t('building.form.title.addBuilding')} />
  );
};
