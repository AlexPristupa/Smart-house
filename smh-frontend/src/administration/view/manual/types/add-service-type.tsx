import { useQueryClient } from 'react-query';
import { Form, Modal } from 'antd';
import { IServiceType } from 'building/interfaces';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { errorHandler } from 'services';
import { useCallback } from 'react';
import { ServiceTypeForm } from '../component/form/types';
import * as React from 'react';
import { useAddServiceType } from '../hooks';

interface CollectionCreateFormProps {
  visible: boolean;
  onOk: () => void;
  onCancel: () => void;
}

export const AddServiceType: React.FC<CollectionCreateFormProps> = ({ visible, onCancel }) => {
  const queryClient = useQueryClient();

  const [form] = Form.useForm<IServiceType>();

  const { mutate, isLoading } = useAddServiceType({
    onMutate: async () => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.SERVICE);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.SERVICE);
      form.resetFields();
      onCancel();
    },
    onError: error => {
      errorHandler(error, form);
    },
  });

  const onFinish = useCallback(
    (values: IServiceType) => {
      mutate(values);
    },
    [mutate],
  );

  const onOk = () => {
    form
      .validateFields()
      .then(values => {
        onFinish(values);
      })
      .catch(info => {
        console.log('Validate Failed:', info);
      });
  };

  const handleCancel = () => {
    form.resetFields();
    onCancel();
  };

  return (
    <Modal visible={visible} title="Добавление типа сервисов" okText="Сохранить" cancelText="Отменить" onOk={onOk} onCancel={handleCancel}>
      <ServiceTypeForm form={form} isLoading={isLoading} onCancel={onCancel} />
    </Modal>
  );
};
