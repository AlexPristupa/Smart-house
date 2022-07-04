import { List } from 'antd';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IBuildingUrlParams } from 'building/interfaces';
import { QueryKey, useQueryClient } from 'react-query';
import { useParams } from 'react-router';
import { useAddDocumentationFiles, useGetDocumentationList } from 'building/hooks/documentation';
import Dragger from 'antd/lib/upload/Dragger';
import { UploadChangeParam } from 'antd/lib/upload';
import { UploadFile } from 'antd/lib/upload/interface';
import styled from 'styled-components';
import { DocumentationListItem } from 'building/components';
import { useTranslation } from 'react-i18next';

const DragAndDrop = styled(Dragger)`
  &.ant-upload.ant-upload-drag {
    text-align: left;
  }
  &:hover {
    cursor: default;
  }
  &.ant-upload.ant-upload-drag:not(.ant-upload-disabled):hover {
    border-color: rgb(217 217 217);
  }
`;

export const DocumentationsTab: React.FC = () => {
  const { buildingId } = useParams<IBuildingUrlParams>();
  const queryClient = useQueryClient();
  const { t } = useTranslation();

  const docQueryKey: QueryKey = [BUILDING_QUERY_KEYS.DOCUMENTATION_LIST, buildingId];

  const { data, isLoading } = useGetDocumentationList(buildingId!);

  const { mutate } = useAddDocumentationFiles({
    onSuccess: () => {
      queryClient.invalidateQueries(docQueryKey);
    },
  });

  const handleChange = ({ fileList }: UploadChangeParam<UploadFile>) => {
    const files = fileList.map(file => file.originFileObj as File);

    mutate(files);
  };

  return (
    <DragAndDrop
      maxCount={50}
      beforeUpload={() => {
        // предотавращение отправки запроса
        return false;
      }}
      multiple
      showUploadList={false}
      onChange={handleChange}
      openFileDialogOnClick={false}
    >
      <List
        locale={{ emptyText: t('messageSystem.noData') }}
        bordered={false}
        loading={isLoading}
        itemLayout="horizontal"
        dataSource={data?.content}
        renderItem={item => <DocumentationListItem item={item} isLoading={isLoading} />}
      />
    </DragAndDrop>
  );
};
