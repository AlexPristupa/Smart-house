import { IServiceType } from 'building/interfaces';
import { useTranslation } from 'react-i18next';
import { useCallback, useState } from 'react';
import { IMenuItemProps, MenuList, WrapperText } from 'ui';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined, StarFilled } from '@ant-design/icons';
import { List, message, Modal, Skeleton } from 'antd';
import { QueryKey, useQueryClient } from 'react-query';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { EditTypeField } from './edit-type-field';
import styled from 'styled-components';
import { useDeleteServiceType } from '../hooks';

const ServiceTypeItem = styled(List.Item)`
  &:hover {
    background-color: inherit;
    cursor: default;
  }
`;

const StarIcon = styled(StarFilled)`
  color: #bebebe;
  font-size: 10px;
  margin-left: 5px;
`;

interface IServiceTypeListItemProps {
  item: IServiceType;
  isLoading: boolean;
}

export const TypesListItem: React.FC<IServiceTypeListItemProps> = ({ item, isLoading: isListLoading }) => {
  const { t } = useTranslation();
  const [isEdit, setIsEdit] = useState(false);

  const queryClient = useQueryClient();

  const typeQueryKey: QueryKey = BUILDING_QUERY_KEYS.SERVICE;

  const handleEdit = useCallback(() => {
    setIsEdit(false);
    queryClient.invalidateQueries(typeQueryKey);
  }, [typeQueryKey, queryClient]);

  const handleCancel = () => {
    setIsEdit(false);
  };

  const { mutate: deleteServiceType } = useDeleteServiceType({
    onMutate: () => {
      queryClient.cancelQueries(typeQueryKey);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(typeQueryKey);
    },
    onError: () => {
      message.info('error');
    },
  });

  const getMenuOptions = (item: IServiceType): IMenuItemProps[] => [
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
            deleteServiceType(item);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  const getEditMenuOptions = (): IMenuItemProps[] => [
    {
      label: t('button.editName'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        setIsEdit(true);
      },
    },
  ];

  return (
    <ServiceTypeItem key={item.id}>
      <Skeleton avatar title={false} loading={isListLoading} active>
        {isEdit ? (
          <EditTypeField type={item} onEdit={handleEdit} onCancel={handleCancel} />
        ) : (
          <List.Item.Meta
            title={
              <WrapperText ellipsis={{ rows: 1, tooltip: item.name }}>
                {item.name}
                {item.pinned && <StarIcon />}
              </WrapperText>
            }
          />
        )}
      </Skeleton>
      {!isEdit && (
        <MenuList children={<EllipsisOutlined />} options={item.pinned || item.linked ? getEditMenuOptions() : getMenuOptions(item)} />
      )}
    </ServiceTypeItem>
  );
};
