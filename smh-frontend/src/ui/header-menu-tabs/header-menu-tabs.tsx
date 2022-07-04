import { Button, Dropdown, Menu } from 'antd';
import { useUsersRouter } from 'administration/view/users/utils';
import { useRouter } from 'administration/hooks';
import { MenuOutlined } from '@ant-design/icons';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { useTranslation } from 'react-i18next';

export const HeaderMenuTabs = () => {
  const { goToBuildingsList, goToManual } = useRouter();
  const { goToUserList } = useUsersRouter();
  const { md } = useBreakpoint();
  const { t } = useTranslation();

  const menu = () => {
    return (
      <Menu mode="horizontal" style={{ fontSize: '16px', borderBottom: 'none', marginLeft: '10px' }}>
        <Menu.Item key="1" onClick={goToBuildingsList}>
          {t('pages.buildings')}
        </Menu.Item>
        <Menu.Item key="2" onClick={goToManual}>
          {t('pages.manuals')}
        </Menu.Item>
        <Menu.Item key="3" onClick={goToUserList}>
          {t('pages.users')}
        </Menu.Item>
      </Menu>
    );
  };

  return md ? (
    menu()
  ) : (
    <Dropdown overlay={menu} trigger={['click']}>
      <Button
        onClick={e => {
          e.stopPropagation();
        }}
        style={{
          width: '32px',
          border: 'none',
        }}
        icon={<MenuOutlined />}
      />
    </Dropdown>
  );
};
