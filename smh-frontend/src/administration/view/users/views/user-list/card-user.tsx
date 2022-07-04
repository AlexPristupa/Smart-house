import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { PageContainer, HeaderButtonsEditDelete, IHeaderButtonsEditDeleteProps, IMenuItemProps, MenuList } from 'ui';
import { Modal } from 'antd';
import { useTranslation } from 'react-i18next';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import { CardUsersMd } from './card-user-md';
import { useDeleteUser, useGetUserData } from 'administration/view/users/hooks';
import { useUsersRouter } from 'administration/view/users/utils';
import { Header } from 'building/components';
import { optimisticUpdatesDelete, useIsRole } from 'services';
import { CardServicesXs } from './card-user-xs';
import { ADMIN_QUERY_KEYS } from 'administration/constants';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useQueryClient } from 'react-query';

export const CardUser: React.FC = () => {
  const { t } = useTranslation();
  const { isRoot } = useIsRole();
  const {
    params: { userId },
  } = useUsersRouter();

  const { goToUserList, gotToUserEdit } = useUsersRouter();

  const { md } = useBreakpoint();
  const queryClient = useQueryClient();

  const { data, isLoading, error } = useGetUserData(parseInt(userId!));

  const { mutate, isLoading: load } = useDeleteUser({
    onMutate: async val => {
      await queryClient.cancelQueries(ADMIN_QUERY_KEYS.USER_LIST);

      const previousBuilding = optimisticUpdatesDelete(val.toString(), BUILDING_QUERY_KEYS.BUILDING_LIST, queryClient);

      return { previousBuilding };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(ADMIN_QUERY_KEYS.USER_LIST);
      goToUserList();
    },
    onError: (context: any) => {
      queryClient.setQueryData(ADMIN_QUERY_KEYS.USER_LIST, context.previousBuilding);
    },
    onSettled: () => {
      queryClient.invalidateQueries(ADMIN_QUERY_KEYS.USER_LIST);
    },
  });

  const propsEditDelete: IHeaderButtonsEditDeleteProps = {
    isLoading,
    editButtonProps: {
      type: 'primary',
      children: t('button.edit'),
      onClick: () => {
        gotToUserEdit(parseInt(userId!));
      },
    },

    deleteButtonProps: {
      children: t('button.delete'),
      onClick: () => {
        Modal.confirm({
          title: t('messageSystem.modal.deleteTitle'),
          okText: t('button.delete'),
          cancelText: t('button.cancel'),
          icon: <ExclamationCircleOutlined />,
          onOk: () => {
            if (userId) mutate(parseInt(userId!));
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
    },
  };
  const propsEdit: IHeaderButtonsEditDeleteProps = {
    isLoading,
    editButtonProps: {
      type: 'primary',
      children: t('button.edit'),
      onClick: () => {
        gotToUserEdit(parseInt(userId!));
      },
    },
  };

  const getMenuOptions = (): IMenuItemProps[] => [
    {
      label: t('button.edit'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        gotToUserEdit(parseInt(userId!));
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
            if (userId) mutate(parseInt(userId!));
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  const getRootOptions = (): IMenuItemProps[] => [
    {
      label: t('button.edit'),
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        gotToUserEdit(parseInt(userId!));
      },
    },
  ];

  const isAvailable = Boolean(data) || error?.code !== 404;

  const checkPermissionHeader = data => {
    switch (true) {
      case isRoot() && data?.role === 'ROOT': {
        return [<HeaderButtonsEditDelete {...propsEdit} />];
      }
      case data?.role !== 'ROOT': {
        return [<HeaderButtonsEditDelete {...propsEditDelete} />];
      }
      default:
        return <></>;
    }
  };
  const checkPermissionMenuOptions = data => {
    switch (true) {
      case isRoot() && data?.role === 'ROOT': {
        return <MenuList children={<EllipsisOutlined />} options={getRootOptions()} />;
      }
      case data?.role !== 'ROOT': {
        return [<MenuList children={<EllipsisOutlined />} options={getMenuOptions()} />];
      }
      default:
        return <></>;
    }
  };

  return (
    <>
      {md ? (
        <Header onBack={goToUserList} extra={checkPermissionHeader(data)} />
      ) : (
        <Header onBack={goToUserList} extra={checkPermissionMenuOptions(data)} />
      )}
      <PageContainer avalibility={isAvailable} isLoading={isLoading || load}>
        {!md ? <CardServicesXs data={data!} /> : <CardUsersMd data={data!} />}
      </PageContainer>
    </>
  );
};
