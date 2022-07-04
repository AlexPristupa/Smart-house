import { baseRules, useGetProfileData } from 'services';
import { useCallback } from 'react';
import { useTranslation } from 'react-i18next';
import { ButtonProps, Form, Input, Select } from 'antd';
import { IUser } from '../../interfaces';
import { useUsersRouter } from '../../utils';
import { USER_FORM_FIELDS } from './constants';
import { IFormProps } from 'building/components';
import { FormWrapper } from 'building/components/forms/form-wrapper';
import { useChangeFormValues } from 'services';

export const UserForm: React.FC<IFormProps<IUser>> = ({ form, initialValues, isLoading, onCancel, onFinish }) => {
  const handleCancel = useCallback(() => onCancel(form), [form, onCancel]);
  const { isValuesChange, onChangeFormValues } = useChangeFormValues();
  const { goToUserList } = useUsersRouter();
  const { t } = useTranslation();
  const { data: dataProfile } = useGetProfileData();

  const roles = ['ADMIN', 'WRITER', 'READER'];

  const cancelButtonProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      goToUserList();
    },
  };
  const IsEqualUserAndProfil = useCallback(() => {
    return dataProfile && initialValues && initialValues.id === dataProfile.id;
  }, [dataProfile, initialValues]);

  return (
    <Form
      name="userForm"
      form={form}
      layout="horizontal"
      labelCol={{ span: 4 }}
      labelAlign="left"
      onValuesChange={onChangeFormValues}
      initialValues={initialValues}
      onFinish={onFinish}
    >
      <FormWrapper isLoading={isLoading} onCancel={handleCancel} cancelButtonProps={cancelButtonProps} valuesChange={isValuesChange}>
        <Form.Item label={t('users.form.lastName')} name={USER_FORM_FIELDS.LAST_NAME} rules={[baseRules.required, baseRules.max255]}>
          <Input />
        </Form.Item>

        <Form.Item label={t('users.form.firstName')} name={USER_FORM_FIELDS.FIRST_NAME} rules={[baseRules.required, baseRules.max255]}>
          <Input />
        </Form.Item>

        <Form.Item label={t('users.form.patronymic')} name={USER_FORM_FIELDS.PATRONYMIC} rules={[baseRules.max255]}>
          <Input />
        </Form.Item>

        <Form.Item label={t('users.form.email')} name={USER_FORM_FIELDS.EMAIL} rules={[baseRules.required, baseRules.max255]}>
          <Input />
        </Form.Item>
        {IsEqualUserAndProfil() ? (
          <Form.Item name={USER_FORM_FIELDS.ROLE} style={{ display: 'none' }} />
        ) : (
          <Form.Item label={t('users.form.role')} name={USER_FORM_FIELDS.ROLE} rules={[baseRules.required]}>
            <Select options={roles.map(role => ({ key: role, label: t<any>(`users.roles.${role}`), value: role }))} />
          </Form.Item>
        )}
      </FormWrapper>
    </Form>
  );
};
