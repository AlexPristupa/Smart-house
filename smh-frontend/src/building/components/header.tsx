import { PageHeader, PageHeaderProps } from 'antd';
import { LeftOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';

export const Header: React.FC<PageHeaderProps> = props => {
  const { t } = useTranslation();
  return (
    <PageHeader
      backIcon={
        <div>
          <LeftOutlined />
          <span>{t('button.back')}</span>
        </div>
      }
      {...props}
    ></PageHeader>
  );
};
