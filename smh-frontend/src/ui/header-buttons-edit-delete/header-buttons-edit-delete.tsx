import { Button, ButtonProps } from 'antd';
import { AddButton } from 'building/components';
import styled from 'styled-components';

export interface IHeaderButtonsEditDeleteProps extends ButtonProps {
  isLoading: boolean;
  editButtonProps: ButtonProps;
  deleteButtonProps?: ButtonProps;
}
const ButtonDelete = styled(Button)`
  margin-right: 16px;
  background: inherit;
  border-color: #ff4d4f;
  box-shadow: none;
  color: #ff4d4f;
  &:hover {
    background: inherit;
    color: #ff8080;
    border-color: #ff8080;
  }
`;

const EditDelete = styled(AddButton)`
  background: inherit;
  color: #107f8c;
  box-shadow: none;
  &:hover {
    background: inherit;
    color: #16909e;
    border-color: #16909e;
  }
`;

export const HeaderButtonsEditDelete: React.FC<IHeaderButtonsEditDeleteProps> = ({ isLoading, editButtonProps, deleteButtonProps }) => {
  return (
    <>
      <EditDelete htmlType="submit" loading={isLoading} {...editButtonProps} />
      {deleteButtonProps && <ButtonDelete danger loading={isLoading} {...deleteButtonProps} />}
    </>
  );
};
