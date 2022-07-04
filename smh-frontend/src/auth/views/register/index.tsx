import { useCallback } from 'react';
import { Form, Input, Button } from 'antd';
import { AUTH_QUERIES, AUTH_TAB } from 'auth/constants';
import { IRegister, RegisterRequest } from 'auth/interfaces';
import { useMutation } from 'react-query';
import { IServerError, isEqualToField, authRequest, errorHandler } from 'services';
import { REGISTER_FIELDS } from './constants';
import { useRouter } from 'auth/hooks/use-router';
import { useTranslation } from 'react-i18next';
import { baseRules } from 'services';

export const Register: React.FC = () => {
  const [form] = Form.useForm();

  const { goToTab } = useRouter();

  const { t } = useTranslation();

  const { mutate, isLoading } = useMutation<IRegister, IServerError, RegisterRequest>(
    data => authRequest({ url: AUTH_QUERIES.REGISTER.URL, method: 'POST', data }),
    {
      onSuccess: () => {
        form.resetFields();
        goToTab(AUTH_TAB.LOGIN);
      },
      onError: error => {
        errorHandler(error, form);
      },
    },
  );

  const onFinish = useCallback(
    (values: IRegister) => {
      mutate(values);
    },
    [mutate],
  );

  return (
    <Form name="register" form={form} layout="vertical" onFinish={onFinish}>
      <Form.Item name={REGISTER_FIELDS.LAST_NAME} rules={[baseRules.required, baseRules.max255]}>
        <Input placeholder={t('placeholder.registration.lastName')} />
      </Form.Item>

      <Form.Item name={REGISTER_FIELDS.FIRST_NAME} rules={[baseRules.required, baseRules.max255]}>
        <Input placeholder={t('placeholder.registration.firstName')} />
      </Form.Item>

      <Form.Item name={REGISTER_FIELDS.PATRONYMIC} rules={[baseRules.max255]}>
        <Input placeholder={t('placeholder.registration.patronymic')} />
      </Form.Item>

      <Form.Item name={REGISTER_FIELDS.EMAIL} rules={[baseRules.required, baseRules.max255, { type: 'email', message: t('rules.email') }]}>
        <Input type="email" placeholder={t('placeholder.registration.email')} />
      </Form.Item>

      <Form.Item name={REGISTER_FIELDS.PASSWORD} rules={[baseRules.required, baseRules.min8, baseRules.max32]}>
        <Input.Password placeholder={t('placeholder.registration.password')} />
      </Form.Item>

      <Form.Item name={REGISTER_FIELDS.PASSWORD_REPEAT} rules={[baseRules.required, isEqualToField<{ password: string }>('password')]}>
        <Input.Password placeholder={t('placeholder.registration.passwordRepeat')} />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" loading={isLoading} style={{ width: '100%' }}>
          {t('button.register')}
        </Button>
      </Form.Item>
    </Form>
  );
};
