import { Button, Dropdown, Menu, ButtonProps } from 'antd';
import React, { useMemo } from 'react';
import cuid from 'cuid';

export interface IMenuItemProps {
  label: string;
  onClick?: (item: any) => void;
  icon?: React.ReactNode;
  danger?: boolean;
}

export interface IMenuListProps {
  options: IMenuItemProps[];
  buttonType?: ButtonProps['type'];
}

export const MenuList: React.FC<IMenuListProps> = ({ options, buttonType, children }) => {
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
    <Dropdown overlay={menu} trigger={['click']}>
      <Button
        type={buttonType}
        onClick={e => {
          e.stopPropagation();
        }}
        style={{
          padding: 0,
          width: '32px',
        }}
      >
        {children}
      </Button>
    </Dropdown>
  );
};
