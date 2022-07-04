import { useCallback } from 'react';
import { Form } from 'antd';
import { HardwareForm } from 'building/components';
import { IHardware, IHardwareObject } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useAddHardware } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useTranslation } from 'react-i18next';
import { errorHandler } from 'services';

export const AddHardware: React.FC = () => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm<IHardware>();
  const { t } = useTranslation();
  const {
    params: { buildingId },
  } = useRouter();

  const { goToBuildingPage } = useRouter();

  const { mutate, isLoading } = useAddHardware({
    onMutate: () => {
      queryClient.cancelQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);
    },
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);
      form.resetFields();
      goToBuildingPage();
    },
    onError: error => {
      errorHandler(error, form);
    },
    onSettled: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);
    },
  });

  const onCancel = useCallback(() => {
    goToBuildingPage();
  }, [goToBuildingPage]);

  const onFinish = useCallback(
    (values: IHardwareObject) => {
      const hardwareObject = {
        ...values,
        entityId: buildingId,
        action: 'new',
        editImage: true,
        editPhoto: true,
      };
      mutate(hardwareObject);
    },
    [mutate, buildingId],
  );

  return (
    <HardwareForm form={form} isLoading={isLoading} onCancel={onCancel} onFinish={onFinish} title={t('hardware.form.title.addHardware')} />
  );
};
