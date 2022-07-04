import { ButtonProps, Card, Modal } from 'antd';
import { Header } from 'building/components/header';
import { HeaderButtonsSaveCancel } from 'ui';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';

const FormTitleH1 = styled.h1`
  font-size: 32px;
`;

interface IFormWrapperProps {
  onCancel: () => void;
  isLoading: boolean;
  addButtonProps?: ButtonProps;
  cancelButtonProps?: ButtonProps;
  valuesChange?: boolean;
  title?: string;
}

export const FormWrapper: React.FC<IFormWrapperProps> = ({
  onCancel,
  isLoading,
  addButtonProps,
  cancelButtonProps,
  children,
  title,
  valuesChange,
}) => {
  const { t } = useTranslation();
  const openModal = () => {
    if (valuesChange) {
      Modal.confirm({
        title: t('messageSystem.modal.warningTitle'),
        okText: t('button.continue'),
        cancelText: t('button.cancel'),
        icon: <ExclamationCircleOutlined />,
        onOk: () => {
          onCancel();
        },
        content: <p>{t('messageSystem.modal.warningContent')}</p>,
      });
    } else {
      onCancel();
    }
  };
  return (
    <Header
      onBack={openModal}
      extra={[<HeaderButtonsSaveCancel addButtonProps={addButtonProps} cancelButtonProps={cancelButtonProps} isLoading={isLoading} />]}
    >
      <Card bordered={false}>
        <FormTitleH1>{title}</FormTitleH1>
        {children}
      </Card>
    </Header>
  );
};
