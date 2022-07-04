import { ButtonProps, Form, Input } from 'antd';
import { IBuilding } from 'building/interfaces';
import { useCallback } from 'react';
import { baseRules } from 'services';
import { IFormProps } from '../interfaces';
import { FormWrapper } from '../form-wrapper';
import { BUILDING_FORM_FIELDS } from './constants';
import { UploadWithPreview } from 'ui';
import { useRouter } from 'building/utils';
import { useTranslation } from 'react-i18next';
import { useChangeFormValues } from 'services';

export const BuildingForm: React.FC<IFormProps<IBuilding>> = ({ form, initialValues, isLoading, onCancel, onFinish, title, error }) => {
  const { isValuesChange, onChangeFormValues } = useChangeFormValues();
  const handleCancel = useCallback(() => onCancel(form), [form, onCancel]);
  const { goToBuildingsList } = useRouter();
  const { t } = useTranslation();

  const cancelButtonProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      goToBuildingsList();
    },
  };

  return (
    <Form
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
        <UploadWithPreview formProps={{ name: 'photo', label: t('building.form.photo') }} uploadProps={{ maxCount: 1 }} />

        <Form.Item label={t('building.form.name')} name={BUILDING_FORM_FIELDS.NAME} rules={[baseRules.required, baseRules.max255]}>
          <Input onClick={() => console.log(form.getFieldsValue())} />
        </Form.Item>

        <Form.Item label={t('building.form.description')} name={BUILDING_FORM_FIELDS.DESCRIPTION} rules={[baseRules.max255]}>
          <Input />
        </Form.Item>

        <Form.Item label={t('building.form.address')} name={BUILDING_FORM_FIELDS.ADDRESS} rules={[baseRules.required]}>
          <Input />
        </Form.Item>
      </FormWrapper>
    </Form>
  );
};
