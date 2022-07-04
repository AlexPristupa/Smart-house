import { Avatar, List, Modal, Skeleton } from 'antd';
import { Header } from 'building/components';
import { useDeleteUser, useGetUserList } from 'administration/view/users/hooks';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined, UserOutlined } from '@ant-design/icons';
import { IMenuItemProps, MenuList, WrapperText } from 'ui';
import React, { useCallback } from 'react';
import { useTranslation } from 'react-i18next';
import { BASE_URLS, getFileSrc, getFio, goToLink, IFileContent, optimisticUpdatesDelete, useIsRole } from 'services';
import { useUsersRouter } from 'administration/view/users/utils';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useQueryClient } from 'react-query';
import { ADMIN_QUERY_KEYS } from 'administration/constants';

export const UserList: React.FC = () => {
  const { data, isLoading } = useGetUserList();
  const { t } = useTranslation();
  const { gotToUserEdit } = useUsersRouter();
  const queryClient = useQueryClient();
  const { isRoot } = useIsRole();

  const { mutate, isLoading: load } = useDeleteUser({
    onMutate: async val => {
      await queryClient.cancelQueries(ADMIN_QUERY_KEYS.USER_LIST);

      const previousBuilding = optimisticUpdatesDelete(val.toString(), BUILDING_QUERY_KEYS.BUILDING_LIST, queryClient);

      return { previousBuilding };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(ADMIN_QUERY_KEYS.USER_LIST);
    },
    onError: (context: any) => {
      queryClient.setQueryData(ADMIN_QUERY_KEYS.USER_LIST, context.previousBuilding);
    },
    onSettled: () => {
      queryClient.invalidateQueries(ADMIN_QUERY_KEYS.USER_LIST);
    },
  });

  const deleteUser = useCallback(
    (id: number) => {
      mutate(id);
    },
    [mutate],
  );

  const getMenuOptions = (id: number): IMenuItemProps[] => [
    {
      label: t('button.edit'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        gotToUserEdit(id);
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
            deleteUser(id);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];
  const getRootMenuOptions = (id: number): IMenuItemProps[] => [
    {
      label: t('button.edit'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        gotToUserEdit(id);
      },
    },
  ];

  const checkPermissionHeader = item => {
    switch (true) {
      case isRoot() && item?.role === 'ROOT': {
        return <MenuList children={<EllipsisOutlined />} options={getRootMenuOptions(item.id)} />;
      }
      case item?.role !== 'ROOT': {
        return <MenuList children={<EllipsisOutlined />} options={getMenuOptions(item.id)} />;
      }
      default:
        return <></>;
    }
  };

  return (
    <>
      <Header title={t('users.title')} />
      <List
        bordered={false}
        loading={isLoading || load}
        itemLayout="horizontal"
        dataSource={data?.content}
        renderItem={item => (
          <List.Item onClick={goToLink(`${BASE_URLS.USERS}/${item.id}`)}>
            <Skeleton avatar title={false} loading={isLoading} active>
              <List.Item.Meta
                avatar={
                  <Avatar
                    src={item.photo && !Array.isArray(item.photo) && getFileSrc(item.photo as IFileContent, true)}
                    icon={!item.photo && <UserOutlined />}
                  />
                }
                title={
                  <WrapperText
                    ellipsis={{
                      rows: 1,
                      tooltip: getFio(item),
                    }}
                  >
                    {getFio(item)}
                  </WrapperText>
                }
                description={t<string | any>(`users.roles.${item.role}`)}
              />
            </Skeleton>
            {checkPermissionHeader(item)}
          </List.Item>
        )}
      />
    </>
  );
};
