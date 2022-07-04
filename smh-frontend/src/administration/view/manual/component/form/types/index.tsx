import { IFormProps } from 'building/components';
import { IServiceType } from 'building/interfaces';
import { Form, Input } from 'antd';
import { baseRules } from 'services';
import { SERVICE_TYPE_FORM_FIELDS } from './constants';

export const ServiceTypeForm: React.FC<IFormProps<IServiceType>> = ({ form, initialValues }) => {
  return (
    <Form name="serviceForm" form={form} layout="vertical" labelAlign="left" initialValues={initialValues}>
      <Form.Item label="Наименование" name={SERVICE_TYPE_FORM_FIELDS.NAME} rules={[baseRules.required, baseRules.max255]}>
        <Input />
      </Form.Item>
    </Form>
  );
};
