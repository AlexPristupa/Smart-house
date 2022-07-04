import { Avatar, ButtonProps, List, Modal, Skeleton } from 'antd';
import { BASE_URLS, getFileSrc, goToLink, IFileContent, optimisticUpdatesDelete, useIsRole } from 'services';
import { AddButton, AddButtonBottom, Header } from 'building/components';
import { IMenuItemProps, MenuList, WrapperText } from 'ui';
import { useRouter } from 'building/utils';
import { useDeleteBuilding, useGetBuildingsList } from 'building/hooks';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useQueryClient } from 'react-query';
import { useCallback } from 'react';
import useBreakpoint from 'antd/lib/grid/hooks/useBreakpoint';
import { ExclamationCircleOutlined, EllipsisOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';

import { useTranslation } from 'react-i18next';

export const BuildingsList: React.FC = () => {
  const queryClient = useQueryClient();
  const { isNotReader } = useIsRole();
  const { goToAddBuilding, goToEditBuilding } = useRouter();
  const { t } = useTranslation();
  const { data, isLoading } = useGetBuildingsList();
  const { md } = useBreakpoint();

  const { mutate, isLoading: load } = useDeleteBuilding({
    onMutate: async val => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);

      const previousBuilding = optimisticUpdatesDelete(val, BUILDING_QUERY_KEYS.BUILDING_LIST, queryClient);

      return { previousBuilding };
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
    },
    onError: (context: any) => {
      queryClient.setQueryData(BUILDING_QUERY_KEYS.BUILDING_LIST, context.previousBuilding);
    },
    onSettled: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.BUILDING_LIST);
    },
  });

  const addButtonProps: ButtonProps = { children: t('button.addObj'), onClick: goToAddBuilding };

  const deleteFacilitie = useCallback(
    (id: number) => {
      mutate(id.toString());
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
        goToEditBuilding(id);
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
            deleteFacilitie(id);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  return (
    <>
      <Header title={t('header.title.objects')} extra={isNotReader() && [md && <AddButton {...addButtonProps} />]} />
      <List
        locale={{ emptyText: t('messageSystem.noData') }}
        bordered={false}
        loading={isLoading || load}
        itemLayout="horizontal"
        dataSource={data?.content}
        renderItem={item => (
          <List.Item onClick={goToLink(`${BASE_URLS.BUILDINGS}/${item.id}`)}>
            <Skeleton avatar title={false} loading={isLoading} active>
              <List.Item.Meta
                avatar={
                  <Avatar src={item.photo && !Array.isArray(item.photo) ? getFileSrc(item.photo as IFileContent, true) : undefined} />
                }
                title={<WrapperText ellipsis={{ rows: 1, tooltip: item.name }}>{item.name}</WrapperText>}
                description={<WrapperText ellipsis={{ rows: 2, tooltip: item.address }}>{item.address}</WrapperText>}
              />
            </Skeleton>
            {isNotReader() && <MenuList children={<EllipsisOutlined />} options={getMenuOptions(item.id)} />}
          </List.Item>
        )}
      />
      <AddButtonBottom {...addButtonProps} />
    </>
  );
};
