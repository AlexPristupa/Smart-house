import { Layout } from 'antd';
import moment from 'moment';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

const Content = styled(Layout.Content)`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Footer = styled(Layout.Footer)`
  text-align: center;
  color: #8c8c8c;
`;

export const AuthLayout: React.FC = ({ children }) => {
  const { t } = useTranslation();
  return (
    <Layout>
      <Content>{children}</Content>
      <Footer>
        {t('footer.title')}, {moment().year()}
      </Footer>
    </Layout>
  );
};
