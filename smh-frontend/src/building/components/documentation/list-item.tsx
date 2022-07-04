import { useCallback, useState } from 'react';
import { List, Modal, Skeleton } from 'antd';
import { useDeleteDocumentation, useDownloadFile } from 'building/hooks';
import { QueryKey, useQueryClient } from 'react-query';
import { IMenuItemProps, MenuList, WrapperText } from 'ui';
import { IListData, useIsRole } from 'services';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, DownloadOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import { useRouter } from 'building/utils';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IDocumentation } from 'building/interfaces';
import { EditDocumentationField } from './edit-doc-field';
import { useTranslation } from 'react-i18next';

interface IDocumentationListItemProps {
  item: IDocumentation;
  isLoading: boolean;
}

export const DocumentationListItem: React.FC<IDocumentationListItemProps> = ({ item, isLoading: isListLoading }) => {
  const { t } = useTranslation();
  const [isEdit, setIsEdit] = useState(false);
  const { isNotReader } = useIsRole();
  const {
    params: { buildingId },
  } = useRouter();
  const queryClient = useQueryClient();

  // eslint-disable-next-line react-hooks/exhaustive-deps
  const docQueryKey: QueryKey = [BUILDING_QUERY_KEYS.DOCUMENTATION_LIST, buildingId];

  const { mutate: deleteDoc } = useDeleteDocumentation({
    onMutate: async () => {
      await queryClient.cancelQueries(docQueryKey);

      const previousDocs = queryClient.getQueryData<IListData<IDocumentation>>(docQueryKey);

      return { previousDocs };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(docQueryKey);
    },
    onError: (context: any) => {
      queryClient.setQueryData(docQueryKey, context.previousDocs);
    },
    onSettled: () => {
      queryClient.invalidateQueries(docQueryKey);
    },
  });

  const { mutate: downloadFile, isLoading: isDownloading } = useDownloadFile();

  const deleteHardware = useCallback(
    (id: number) => {
      deleteDoc(id.toString());
    },
    [deleteDoc],
  );

  const handleEdit = useCallback(() => {
    setIsEdit(false);
    queryClient.invalidateQueries(docQueryKey);
  }, [docQueryKey, queryClient]);

  const handleCancel = () => {
    setIsEdit(false);
  };

  const getMenuOptions = (item: IDocumentation): IMenuItemProps[] => [
    {
      label: t('button.editName'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        setIsEdit(true);
      },
    },
    {
      label: t('button.download'),
      icon: <DownloadOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        downloadFile(item.file);
      },
    },
    {
      label: t('button.delete'),
      icon: <DeleteOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();

        Modal.confirm({
          title: t('messageSystem.modal.deleteTitle'),
          okText: t('button.delete'),
          cancelText: t('button.cancel'),
          icon: <ExclamationCircleOutlined />,
          onOk: () => {
            deleteHardware(item.id);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  const isLoading = isListLoading || isDownloading;

  return (
    <List.Item onClick={() => downloadFile(item.file)}>
      <Skeleton avatar title={false} loading={isLoading} active>
        {isEdit ? (
          <EditDocumentationField doc={item} onEdit={handleEdit} onCancel={handleCancel} />
        ) : (
          <List.Item.Meta title={<WrapperText ellipsis={{ rows: 1, tooltip: item.name }}>{item.name}</WrapperText>} />
        )}
      </Skeleton>
      {isNotReader() && <MenuList children={<EllipsisOutlined />} options={getMenuOptions(item)} />}
    </List.Item>
  );
};
