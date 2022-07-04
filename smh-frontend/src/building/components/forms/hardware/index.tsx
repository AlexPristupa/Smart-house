import { ButtonProps, Form, Input } from 'antd';
import { IHardware } from 'building/interfaces';
import { useCallback } from 'react';
import { baseRules, useChangeFormValues } from 'services';
import { IFormProps } from '../interfaces';
import { FormWrapper } from '../form-wrapper';
import { HARDWARE_FORM_FIELDS } from './constants';
import { UploadWithPreview } from 'ui';
import { useRouter } from 'building/utils';
import { DatePicker } from 'ui';
import { useTranslation } from 'react-i18next';

export const HardwareForm: React.FC<IFormProps<IHardware>> = ({ form, initialValues, isLoading, onCancel, onFinish, title, error }) => {
  const handleCancel = useCallback(() => onCancel(form), [form, onCancel]);
  const { isValuesChange, onChangeFormValues } = useChangeFormValues();
  const { goToBuilding } = useRouter();
  const { t } = useTranslation();

  const cancelButtonProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      goToBuilding();
    },
  };

  return (
    <Form
      name="hardwareForm"
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
        <UploadWithPreview formProps={{ name: 'photo', label: t('hardware.form.photo') }} uploadProps={{ maxCount: 1 }} />

        <Form.Item label={t('hardware.card.nameBuilding')} name={HARDWARE_FORM_FIELDS.NAME} rules={[baseRules.required, baseRules.max255]}>
          <Input onClick={() => console.log(form.getFieldsValue())} placeholder={t('messageSystem.placeholder')} />
        </Form.Item>

        <Form.Item label={t('hardware.card.model')} name={HARDWARE_FORM_FIELDS.MODEL} rules={[baseRules.required, baseRules.max255]}>
          <Input placeholder={t('messageSystem.placeholder')} />
        </Form.Item>

        <Form.Item
          label={t('hardware.card.serialNumber')}
          name={HARDWARE_FORM_FIELDS.SERIALNUMBER}
          rules={[baseRules.required, baseRules.max255]}
        >
          <Input placeholder={t('messageSystem.placeholder')} />
        </Form.Item>

        <Form.Item label={t('hardware.card.installedAt')} name={HARDWARE_FORM_FIELDS.INSTALLERAT} rules={[baseRules.required]}>
          <DatePicker />
        </Form.Item>

        <Form.Item label={t('hardware.card.expiresAt')} name={HARDWARE_FORM_FIELDS.EXPIERSAT} rules={[baseRules.required]}>
          <DatePicker />
        </Form.Item>
        <Form.Item
          label={t('hardware.card.installer')}
          name={HARDWARE_FORM_FIELDS.INSTALLER}
          rules={[baseRules.required, baseRules.max255]}
        >
          <Input placeholder={t('messageSystem.placeholder')} />
        </Form.Item>
      </FormWrapper>
    </Form>
  );
};
