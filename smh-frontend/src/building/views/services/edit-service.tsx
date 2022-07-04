import { useCallback } from 'react';
import { Form } from 'antd';
import { ServiceForm } from 'building/components';
import { IServiceForm } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useEditService, useGetServiceEditData } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { errorHandler, parseTime } from 'services';
import { useTranslation } from 'react-i18next';

export const EditService: React.FC = () => {
  const { t } = useTranslation();
  const {
    params: { contentId, buildingId },
    goToBuilding,
    goToContentPage,
  } = useRouter();

  const queryClient = useQueryClient();

  const { data, isLoading: isDocLoading } = useGetServiceEditData(parseInt(contentId!));

  const [form] = Form.useForm<IServiceForm>();

  const { mutate, isLoading: isSubmiting } = useEditService({
    onMutate: async () => {
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.EDIT_SERVICE, contentId]);
    },
    onSuccess: async () => {
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.EDIT_SERVICE, contentId]);
      form.resetFields();
      goToContentPage(parseInt(contentId!));
    },
    onError: async err => {
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.EDIT_SERVICE, contentId]);
      errorHandler(err, form);
    },
    onSettled: async () => {
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.EDIT_SERVICE, contentId]);
      await queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE, contentId]);
    },
  });

  const onCancel = useCallback(() => {
    goToBuilding();
  }, [goToBuilding]);

  const formatTime = (data: string) => {
    // HH:mm
    return data.length > 5 ? `${parseTime(data, '{h}:{i}')}:00` : `${data}:00`;
  };

  const onFinish = useCallback(
    (values: IServiceForm) => {
      const tempStartTime = new Date(`${parseTime(values.startTime, '{y}-{m}-{d}')} ${formatTime(values.timeStart)}`).toISOString();
      const tempFinishtTime = new Date(`${parseTime(values.finishTime, '{y}-{m}-{d}')} ${formatTime(values.timeFinish)}`).toISOString();
      const editValues = {
        ...values,
        startTime: tempStartTime,
        finishTime: tempFinishtTime,
        id: parseInt(contentId!),
      };
      mutate(editValues);
    },
    [contentId, mutate],
  );

  const isLoading = isDocLoading || isSubmiting;

  if (data) {
    return (
      <ServiceForm
        form={form}
        initialValues={data}
        isLoading={isLoading}
        onCancel={onCancel}
        onFinish={onFinish}
        title={t('service.form.title.editService')}
      />
    );
  }

  return null;
};
