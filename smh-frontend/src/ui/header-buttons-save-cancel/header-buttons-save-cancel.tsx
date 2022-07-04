import { ButtonProps } from 'antd';
import { AddButton } from 'building/components';
import { useRouter } from 'building/utils';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

const LayerButtonSaveCancel = styled.div`
  display: flex;
`;

const ButtonCancel = styled(AddButton)`
  margin-right: 16px;
  background: inherit;
  border-color: rgb(217 217 217);
  box-shadow: none;
  color: black;
  &:hover {
    border-color: #107f8c;
    color: #107f8c;
    background: inherit;
  }
`;

interface IProps extends ButtonProps {
  isLoading: boolean;
  addButtonProps?: ButtonProps;
  cancelButtonProps?: ButtonProps;
}

export const HeaderButtonsSaveCancel: React.FC<IProps> = ({ isLoading, addButtonProps, cancelButtonProps }) => {
  const {
    goToContentPage,
    goToBuildingPage,
    params: { action, contentId },
  } = useRouter();

  const { t } = useTranslation();

  const addButtonSaveProps: ButtonProps = {
    children: t('button.save'),
  };
  const addButtonCancelProps: ButtonProps = {
    children: t('button.cancel'),
    onClick: () => {
      if (action === 'edit' && contentId) {
        goToContentPage(parseInt(contentId));
      } else {
        goToBuildingPage();
      }
    },
  };

  return (
    <LayerButtonSaveCancel>
      <ButtonCancel {...(cancelButtonProps || addButtonCancelProps)} />
      <AddButton {...(addButtonProps || addButtonSaveProps)} htmlType="submit" loading={isLoading} />
    </LayerButtonSaveCancel>
  );
};
