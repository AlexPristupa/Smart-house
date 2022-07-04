import { Form, FormItemProps, Modal, Upload, UploadProps } from 'antd';
import { getFileSrc, IFileContent, useImagePreview } from 'services';
import { PlusOutlined } from '@ant-design/icons';
import { UploadChangeParam, UploadFile } from 'antd/lib/upload/interface';
import { ReactNode, useCallback, useState } from 'react';
import { useTranslation } from 'react-i18next';

interface IUploadProps extends Omit<UploadProps, 'fileList'> {
  label?: ReactNode;
  fileList?: IFileContent[] | IFileContent | UploadFile[];
}

const CustomUpload: React.FC<IUploadProps> = ({ label, fileList, onChange, ...restProps }) => {
  const { t } = useTranslation();
  const files: UploadFile[] =
    Array.isArray(fileList) && fileList.length
      ? (fileList as IFileContent[])
          .filter(file => Boolean(file.contentId))
          .map(({ contentId, contentLength, name }) => ({
            uid: contentId,
            size: contentLength,
            url: getFileSrc(contentId),
            name,
          }))
      : [];

  const singleFile: UploadFile[] | undefined =
    fileList && !Array.isArray(fileList)
      ? [
          {
            uid: fileList!.contentId,
            size: fileList!.contentLength,
            url: getFileSrc(fileList!.contentId),
            name: fileList!.name,
          },
        ]
      : undefined;

  const [filesState, setFilesState] = useState(singleFile || files);

  const handleChange = useCallback(
    (fileEvent: UploadChangeParam<UploadFile>) => {
      setFilesState(fileEvent.fileList);
      onChange && onChange(fileEvent);
    },
    [onChange],
  );

  return (
    <Upload
      {...restProps}
      onChange={handleChange}
      style={{ width: '300px' }}
      listType="picture-card"
      fileList={filesState}
      beforeUpload={() => {
        // предотавращение отправки запроса
        return false;
      }}
      showUploadList={true}
    >
      <div>
        <PlusOutlined />
        <div style={{ marginTop: 8 }}>{label || t('button.load')}</div>
      </div>
    </Upload>
  );
};

interface IUploadWithPreviewProps {
  formProps: FormItemProps;
  uploadProps?: UploadProps;
}

export const UploadWithPreview: React.FC<IUploadWithPreviewProps> = ({ formProps: { label, ...restFormProps }, uploadProps }) => {
  const { preview, handlePreview, handleCancel } = useImagePreview();

  const normFile = e => {
    if (Array.isArray(e)) {
      return e;
    }
    return e && e.fileList;
  };

  return (
    <>
      <Form.Item {...restFormProps} valuePropName="fileList" getValueFromEvent={normFile}>
        <CustomUpload {...uploadProps} label={label} onPreview={handlePreview} />
      </Form.Item>
      <Modal visible={preview.isVisible} title={preview.title} footer={null} width="80%" onCancel={handleCancel}>
        <img alt="example" style={{ width: '100%' }} src={preview.image} />
      </Modal>
    </>
  );
};
