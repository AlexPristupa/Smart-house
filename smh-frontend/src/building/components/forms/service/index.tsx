import { ButtonProps, Form, Input, Select } from 'antd';
import { IServiceForm } from 'building/interfaces';
import { useCallback } from 'react';
import { baseRules, useChangeFormValues } from 'services';
import { IFormProps } from '../interfaces';
import { FormWrapper } from '../form-wrapper';
import { SERVICE_FORM_FIELDS } from './constants';
import { useRouter } from 'building/utils';
import { useGetServiceTypes } from 'building/hooks';
import { DatePicker, TimePicker } from 'ui';
import { ResolutionRadioButtons } from 'building/components';
import { useTranslation } from 'react-i18next';

export const ServiceForm: React.FC<IFormProps<IServiceForm>> = ({ form, initialValues, isLoading, onCancel, onFinish, title, error }) => {
  const handleCancel = useCallback(() => onCancel(form), [form, onCancel]);
  const { isValuesChange, onChangeFormValues } = useChangeFormValues();
  const { goToBuilding } = useRouter();
  const { t } = useTranslation();

  const { data: options } = useGetServiceTypes();

  const cancelButtonProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      goToBuilding();
    },
  };

  const format = 'HH:mm';

  return (
    <Form
      name="serviceForm"
      form={form}
      layout="horizontal"
      labelCol={{ span: 4 }}
      labelAlign="left"
      initialValues={initialValues}
      onValuesChange={onChangeFormValues}
      onFinish={onFinish}
    >
      <FormWrapper
        isLoading={isLoading}
        onCancel={handleCancel}
        cancelButtonProps={cancelButtonProps}
        title={title}
        valuesChange={isValuesChange}
      >
        <Form.Item label={t('service.card.name')} name={SERVICE_FORM_FIELDS.NAME} rules={[baseRules.required, baseRules.max255]}>
          <Input onClick={() => console.log(form.getFieldsValue())} />
        </Form.Item>

        <Form.Item label={t('service.card.type')} name={SERVICE_FORM_FIELDS.TYPE} rules={[baseRules.required]}>
          <Select options={options?.content.map(option => ({ key: option.id, label: option.name, value: option.id }))} />
        </Form.Item>

        <Form.Item label={t('service.card.startTime')} required>
          <div style={{ display: 'flex', alignItems: 'baseline' }}>
            <Form.Item name={SERVICE_FORM_FIELDS.START_TIME} rules={[baseRules.required]}>
              <DatePicker />
            </Form.Item>
            <div style={{ margin: '0px 5px' }}>–</div>
            <Form.Item name={SERVICE_FORM_FIELDS.TIME_START}>
              <TimePicker format={format} />
            </Form.Item>
          </div>
        </Form.Item>

        <Form.Item label={t('service.card.finishTime')} required>
          <div style={{ display: 'flex', alignItems: 'baseline' }}>
            <Form.Item name={SERVICE_FORM_FIELDS.FINISH_TIME} rules={[baseRules.required]}>
              <DatePicker />
            </Form.Item>
            <div style={{ margin: '0px 5px' }}>–</div>
            <Form.Item name={SERVICE_FORM_FIELDS.TIME_FINISH}>
              <TimePicker format={format} />
            </Form.Item>
          </div>
        </Form.Item>

        <ResolutionRadioButtons label={t('service.card.statusTitle')} name={SERVICE_FORM_FIELDS.RESOLUTION} />
        <Form.Item label={t('service.card.description')} name={SERVICE_FORM_FIELDS.DESCRIPTION} rules={[baseRules.max255]}>
          <Input />
        </Form.Item>
      </FormWrapper>
    </Form>
  );
};
