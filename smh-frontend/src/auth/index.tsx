import { Card, Tabs } from 'antd';
import { useCallback, useEffect } from 'react';
import { AuthLayout, PageContainer, LogoIcons } from 'ui';
import { AUTH_TAB, AUTH_TAB_VALUES } from './constants';
import { Login, Register } from './views';
import { useRouter } from './hooks';
import { useTranslation } from 'react-i18next';
import { clearAuthToken } from 'services';

const { TabPane } = Tabs;

export const AuthPage: React.FC = () => {
  const { t } = useTranslation();

  useEffect(() => {
    clearAuthToken();
  }, []);

  const {
    params: { authType },
    goToTab,
  } = useRouter();

  const handleChangeTab = useCallback((key: string) => goToTab(key), [goToTab]);

  return (
    <PageContainer avalibility={Boolean(authType && AUTH_TAB_VALUES.includes(authType))}>
      <AuthLayout>
        <Card bordered title={<LogoIcons.Logo height="24px" />} style={{ width: '350px' }}>
          <Tabs defaultActiveKey={AUTH_TAB.LOGIN} activeKey={authType} onTabClick={handleChangeTab}>
            <TabPane tab={t('authPage.tabName.login')} key={AUTH_TAB.LOGIN}>
              <Login />
            </TabPane>
            <TabPane tab={t('authPage.tabName.registration')} key={AUTH_TAB.REGISTER}>
              <Register />
            </TabPane>
          </Tabs>
        </Card>
      </AuthLayout>
    </PageContainer>
  );
};
