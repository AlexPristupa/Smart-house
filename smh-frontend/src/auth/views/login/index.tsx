import { Form, Input, Button } from 'antd';
import { LOGIN_FIELDS } from './constants';
import { useCallback } from 'react';
import { useMutation } from 'react-query';
import { authRequest, BASE_URLS, historyWithRefresh, IAuthResponse, IServerError, setAuthToken } from 'services';
import { ILogin } from 'auth/interfaces';
import { AUTH_QUERIES } from 'auth/constants';
import { Alert } from 'auth/components';
import { useTranslation } from 'react-i18next';
import { baseRules } from 'services';
import { useUserRole } from 'services';

export const Login: React.FC = () => {
  const { t } = useTranslation();
  const [form] = Form.useForm();

  const { setUserRole } = useUserRole();

  const { mutate, isLoading, error } = useMutation<IAuthResponse, IServerError[], ILogin>(
    data => authRequest({ url: AUTH_QUERIES.LOGIN.URL, method: 'POST', data }),
    {
      onSuccess: async ({ accessToken, refreshToken, userInfo }) => {
        setUserRole(userInfo);
        await setAuthToken({ accessToken, refreshToken });
        historyWithRefresh.push(BASE_URLS.BUILDINGS);
      },
    },
  );

  const onFinish = useCallback(
    (values: ILogin) => {
      mutate(values);
    },
    [mutate],
  );

  return (
    <Form name="login" form={form} layout="vertical" initialValues={{ remember: true }} onFinish={onFinish}>
      <Form.Item name={LOGIN_FIELDS.USERNAME} rules={[baseRules.required, baseRules.max255]}>
        <Input type="email" placeholder={t('placeholder.login.username')} />
      </Form.Item>

      <Form.Item name={LOGIN_FIELDS.PASSWORD} rules={[baseRules.required]}>
        <Input.Password placeholder={t('placeholder.login.password')} />
      </Form.Item>

      {error && <Alert message={error} type="error" showIcon />}

      <Form.Item>
        <Button type="primary" htmlType="submit" loading={isLoading} style={{ width: '100%' }}>
          {t('button.login')}
        </Button>
      </Form.Item>
    </Form>
  );
};
