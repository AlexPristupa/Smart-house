import { useCallback } from 'react';
import { Form } from 'antd';
import { HardwareForm } from 'building/components';
import { IHardware, IHardwareObject } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useEditHardware, useGetHardwareData } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useTranslation } from 'react-i18next';
import { errorHandler } from 'services';

export const EditHardware: React.FC = () => {
  const { t } = useTranslation();
  const {
    params: { contentId },
    goToBuildingPage,
  } = useRouter();
  const queryClient = useQueryClient();
  const { data, isLoading: isGetLoading } = useGetHardwareData(contentId!);

  const [form] = Form.useForm<IHardware>();

  const { goToContentPage } = useRouter();

  const { mutate, isLoading: isSubmiting } = useEditHardware({
    onMutate: async () => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.HARDWARE);
    },
    onSuccess: () => {
      queryClient.resetQueries(BUILDING_QUERY_KEYS.HARDWARE);
      form.resetFields();
      goToBuildingPage();
    },
    onError: error => {
      errorHandler(error, form);
    },
  });

  const onCancel = useCallback(
    (id: number) => {
      goToContentPage(id);
    },
    [goToContentPage],
  );

  const onFinish = useCallback(
    (values: IHardwareObject) => {
      const hardwareObject = {
        ...values,
        entityId: contentId,
        action: 'edit',
        editImage: JSON.stringify(data?.images) !== JSON.stringify(values?.images),
        editPhoto: JSON.stringify(data?.photo) !== JSON.stringify(values?.photo),
      };
      mutate(hardwareObject);
    },
    [contentId, data?.images, data?.photo, mutate],
  );

  const isLoading = isGetLoading || isSubmiting;

  if (data) {
    return (
      <HardwareForm
        form={form}
        isLoading={isLoading}
        initialValues={data}
        onCancel={() => onCancel(parseInt(contentId!))}
        onFinish={onFinish}
        title={t('hardware.form.title.editHardware')}
      />
    );
  }

  return null;
};
