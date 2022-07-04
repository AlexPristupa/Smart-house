import { Layout, Avatar, Row, Col } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import moment from 'moment';
import styled from 'styled-components';
import { LogoIcons, MenuList, HeaderMenuTabs, BadgeNotifications } from 'ui';
import { useTranslation } from 'react-i18next';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { BASE_URLS, useUserRole } from 'services';
import { useHistory } from 'react-router';
import { useRouter } from 'building/utils';

const { Header } = Layout;

const ContentWrapper = styled.div`
  width: 100%;
  max-width: 1280px;
`;

const Content = styled(Layout.Content)`
  display: flex;
  justify-content: center;
`;

const Footer = styled(Layout.Footer)`
  text-align: center;
  color: #8c8c8c;
`;
const logoStyle = { display: 'block', alignSelf: 'center', cursor: 'pointer' };
const mobLogoStyle = { ...logoStyle, marginLeft: '10px' };

export const MainLayout: React.FC = ({ children }) => {
  const { t } = useTranslation();
  const { getUserRole } = useUserRole();
  const { goToBuildingsList } = useRouter();
  const { md } = useBreakpoint();
  const isPermission = (): boolean => {
    return getUserRole() === 'ADMIN' || getUserRole() === 'ROOT';
  };

  const { push } = useHistory();

  return (
    <Layout>
      <Header>
        <Row justify="space-between" align="middle">
          <Col>
            <Row>
              {md ? (
                <>
                  <LogoIcons.Logo height="24px" style={logoStyle} onClick={goToBuildingsList} />
                  {isPermission() && <HeaderMenuTabs />}
                </>
              ) : (
                <>
                  {isPermission() && <HeaderMenuTabs />}
                  <LogoIcons.MobLogo height="24px" style={mobLogoStyle} onClick={goToBuildingsList} />
                </>
              )}
            </Row>
          </Col>
          <Col>
            <Row gutter={[20, 0]} align="middle">
              <Col>{<BadgeNotifications />}</Col>
              <Col>
                <MenuList
                  buttonType="link"
                  options={[
                    {
                      label: t('profile.title'),
                      onClick: () => push(BASE_URLS.PROFILE),
                    },
                    {
                      label: t('button.logout'),
                      onClick: () => push(`${BASE_URLS.AUTH}/Login`),
                    },
                  ]}
                >
                  <Avatar size="large" icon={<UserOutlined />} />
                </MenuList>
              </Col>
            </Row>
          </Col>
        </Row>
      </Header>
      <Content>
        <ContentWrapper>{children}</ContentWrapper>
      </Content>
      <Footer>
        {t('footer.title')}, {moment().year()}
      </Footer>
    </Layout>
  );
};
