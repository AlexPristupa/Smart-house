import { Dropdown, Menu } from 'antd';
import { useMemo } from 'react';
import cuid from 'cuid';
import { IMenuListProps } from 'ui/menu-list';

interface IMenuButtonHeaderProps extends IMenuListProps {
  label?: string;
  onClick?: () => void;
}

export const MenuButtonHeader: React.FC<IMenuButtonHeaderProps> = ({ label, onClick, options }) => {
  const menu = useMemo(
    () => (
      <Menu>
        {options.map(({ label, ...restProps }) => (
          <Menu.Item key={cuid()} {...restProps}>
            {label}
          </Menu.Item>
        ))}
      </Menu>
    ),
    [options],
  );

  return (
    <Dropdown.Button onClick={onClick} overlay={menu}>
      {label}
    </Dropdown.Button>
  );
};
