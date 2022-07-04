import { useCallback, useState } from 'react';
import { Button, Input, Space } from 'antd';
import styled from 'styled-components';
import { IDocumentation } from 'building/interfaces';
import { useEditDocumentationFile } from 'building/hooks';
import { CheckOutlined, CloseOutlined } from '@ant-design/icons';
import useBreakpoint from 'antd/es/grid/hooks/useBreakpoint';
import { useTranslation } from 'react-i18next';

interface IEditDocumentationFieldProps {
  doc: IDocumentation;
  onEdit?: (value: string) => void;
  onCancel?: () => void;
}

const FieldContainer = styled.div`
  display: flex;
  width: 100%;
`;

const WrapperSpace = styled(Space)`
  margin: 0 12px;
`;

const getNameFile = (file: string) => {
  const arrNameFile: Array<string> = file.split('.');
  arrNameFile.splice(arrNameFile.length - 1, 1);

  return arrNameFile.join('.');
};

const getExtensionFile = (file: string) => {
  const arrNameFile: Array<string> = file.split('.');

  return arrNameFile[arrNameFile.length - 1];
};

export const EditDocumentationField: React.FC<IEditDocumentationFieldProps> = ({ doc, onEdit, onCancel }) => {
  const [value, setValue] = useState(getNameFile(doc.name));

  const { mutate: editDoc, isLoading } = useEditDocumentationFile(doc.id, {
    onSuccess: () => {
      onEdit && onEdit(value);
    },
  });

  const { md } = useBreakpoint();

  const { t } = useTranslation();

  const handleChange = useCallback(
    e => {
      e.stopPropagation();
      editDoc(`${value}.${getExtensionFile(doc.name)}`);
    },
    [doc.name, editDoc, value],
  );

  const handleCancel = e => {
    e.stopPropagation();
    onCancel && onCancel();
  };

  const handleInputChange = useCallback(e => {
    e.stopPropagation();
    setValue(e.target.value);
  }, []);

  return (
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
  );
};
