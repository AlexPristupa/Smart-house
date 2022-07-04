import { useCallback, useState } from 'react';
import { Button, Input, Space } from 'antd';
import styled from 'styled-components';
import { IServiceType } from 'building/interfaces';
import { useEditServiceType } from '../hooks';
import { CheckOutlined, CloseOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';
import useBreakpoint from 'antd/es/grid/hooks/useBreakpoint';

interface IEditTypeFieldProps {
  type: IServiceType;
  onEdit?: (value: string) => void;
  onCancel?: () => void;
}

const FieldContainer = styled.div`
  display: flex;
  width: 100%;
`;

const FieldWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

const ErrorMessage = styled.div`
  margin-top: 10px;
  margin-left: 5px;
  color: red;
`;

const WrapperSpace = styled(Space)`
  margin: 0 12px;
`;

export const EditTypeField: React.FC<IEditTypeFieldProps> = ({ type, onEdit, onCancel }) => {
  const [value, setValue] = useState(type.name);
  const { t } = useTranslation();
  const { md } = useBreakpoint();
  const {
    mutate: editType,
    isLoading,
    error,
  } = useEditServiceType(type.id, {
    onSuccess: () => {
      onEdit && onEdit(value);
    },
  });

  const handleChange = useCallback(
    e => {
      e.stopPropagation();
      editType({
        ...type,
        name: value,
      });
    },
    [type, editType, value],
  );

  const handleCancel = e => {
    e.stopPropagation();
    onCancel && onCancel();
  };

  const handleInputChange = e => {
    e.stopPropagation();
    setValue(e.target.value);
  };

  return (
    <FieldWrapper>
      <FieldContainer>
        <Input value={value} onClick={e => e.stopPropagation()} onChange={handleInputChange} />
        <WrapperSpace>
          {md ? (
            <>
              <Button loading={isLoading} type="primary" onClick={handleChange}>
                {t('button.save')}
              </Button>
              <Button onClick={handleCancel}>{t('button.cancel')}</Button>
            </>
          ) : (
            <>
              <Button loading={isLoading} type="primary" onClick={handleChange} icon={<CheckOutlined />} />
              <Button onClick={handleCancel} icon={<CloseOutlined />} />
            </>
          )}
        </WrapperSpace>
      </FieldContainer>
      {error && <ErrorMessage>{t<any>(`rules.${error[0].message}`)}</ErrorMessage>}
    </FieldWrapper>
  );
};
