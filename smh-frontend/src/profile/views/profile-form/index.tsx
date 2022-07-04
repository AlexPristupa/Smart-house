import { ButtonProps, Form, Input } from 'antd';
import { useCallback } from 'react';
import { baseRules, BASE_URLS, IProfile, useGetProfileData, useChangeFormValues, QUERY_KEYS } from 'services';
import { FormWrapper } from 'building/components/forms/form-wrapper';
import { PROFILE_FORM_FIELDS } from './constants';
import { DatePicker, UploadWithPreview } from 'ui';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router';
import { useQueryClient } from 'react-query';
import { useEditProfile } from 'profile/hooks';

export const EditProfileForm: React.FC = () => {
  const [form] = Form.useForm<IProfile>();
  const { push } = useHistory();

  const { data, isLoading: isDataLoading } = useGetProfileData();

  const handleCancel = useCallback(() => push(BASE_URLS.PROFILE), [push]);
  const { t } = useTranslation();
  const { isValuesChange, onChangeFormValues } = useChangeFormValues();

  const queryClient = useQueryClient();

  const cancelButtonProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      push(BASE_URLS.PROFILE);
    },
  };

  const { mutate, isLoading: isEditingProfile } = useEditProfile();

  const onFinish = useCallback(
    (values: IProfile) => {
      mutate(values, {
        onSuccess: () => {
          queryClient.resetQueries([QUERY_KEYS.PROFILE]);
          form.resetFields();
          push(BASE_URLS.PROFILE);
        },
      });
    },
    [form, mutate, push, queryClient],
  );

  const isLoading = isDataLoading || isEditingProfile;

  if (data) {
    return (
      <Form
        name="editProfileForm"
        form={form}
        layout="horizontal"
        labelCol={{ span: 4 }}
        labelAlign="left"
        initialValues={data}
        onValuesChange={onChangeFormValues}
        onFinish={onFinish}
      >
        <FormWrapper
          isLoading={isLoading}
          onCancel={handleCancel}
          cancelButtonProps={cancelButtonProps}
          title={t('profile.editProfile')}
          valuesChange={isValuesChange}
        >
          <UploadWithPreview
            formProps={{ name: PROFILE_FORM_FIELDS.PHOTO, label: t('building.form.photo') }}
            uploadProps={{ maxCount: 1 }}
          />

          <Form.Item label={t('profile.lastName')} name={PROFILE_FORM_FIELDS.LAST_NAME} rules={[baseRules.required, baseRules.max255]}>
            <Input />
          </Form.Item>

          <Form.Item label={t('profile.firstName')} name={PROFILE_FORM_FIELDS.FIRST_NAME} rules={[baseRules.required, baseRules.max255]}>
            <Input />
          </Form.Item>

          <Form.Item label={t('profile.patronymic')} name={PROFILE_FORM_FIELDS.PATRONYMIC} rules={[baseRules.max255]}>
            <Input />
          </Form.Item>

          <Form.Item label={t('profile.email')} name={PROFILE_FORM_FIELDS.EMAIL} rules={[baseRules.required, baseRules.max255]}>
            <Input type="email" />
          </Form.Item>

          <Form.Item label={t('profile.birthDate')} name={PROFILE_FORM_FIELDS.BIRTH_DATE}>
            <DatePicker />
          </Form.Item>
        </FormWrapper>
      </Form>
    );
  }
  return null;
};
