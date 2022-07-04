import { Button, ButtonProps } from 'antd';

export const AddButton: React.FC<ButtonProps> = ({ children, ...props }) => {
  return (
    <Button {...props} type="primary">
      {children}
    </Button>
  );
};
