import { List, Modal, Skeleton } from 'antd';
import { BUILDING_QUERY_KEYS, MENU_HEADER } from 'building/constants';
import { IBuildingUrlParams, IService } from 'building/interfaces';
import { useParams } from 'react-router';
import { IconForList, IMenuItemProps, MenuList, WrapperText } from 'ui';
import { useCallback } from 'react';
import { DeleteOutlined, EditOutlined, EllipsisOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import { useGetServiceList } from 'building/hooks';
import { useQueryClient } from 'react-query';
import { useRouter } from 'building/utils';
import { useDeleteService } from 'building/hooks';
import { useTranslation } from 'react-i18next';
import { optimisticUpdatesDelete, useIsRole } from 'services';

export const ServicesTab: React.FC = () => {
  const { t } = useTranslation();
  const { buildingId } = useParams<IBuildingUrlParams>();
  const queryClient = useQueryClient();
  const { goToEditPage, goToContentPage } = useRouter();
  const { isNotReader } = useIsRole();
  const { data, isLoading } = useGetServiceList(buildingId!);

  const { mutate } = useDeleteService({
    onMutate: async val => {
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId!]);

      const previousService = optimisticUpdatesDelete<IService>(
        val.toString(),
        [BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId!],
        queryClient,
      );

      return { previousService };
    },
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
    },
    onError: (context: any) => {
      queryClient.setQueryData([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId], context.previousService);
    },
    onSettled: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId]);
    },
  });

  const deleteHardware = useCallback(
    (id: number) => {
      mutate(id);
    },
    [mutate],
  );

  const getMenuOptions = (id: number): IMenuItemProps[] => [
    {
      label: MENU_HEADER.EDIT,
      icon: <EditOutlined />,
      onClick: ({ domEvent }) => {
        const e = domEvent as Event;
        e.stopPropagation();
        goToEditPage(id.toString());
      },
    },
    {
      label: MENU_HEADER.DELETE,
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
            deleteHardware(id);
          },
          content: <p>{t('messageSystem.modal.deleteContent')}</p>,
        });
      },
      danger: true,
    },
  ];

  return (
    <List
      locale={{ emptyText: t('messageSystem.noData') }}
      bordered={false}
      loading={isLoading}
      itemLayout="horizontal"
      dataSource={data?.content}
      renderItem={item => {
        return (
          <List.Item onClick={() => goToContentPage(item.id)}>
            <Skeleton avatar title={false} loading={isLoading} active>
              <List.Item.Meta
                avatar={<IconForList str={item.name} />}
                title={<WrapperText ellipsis={{ rows: 1, tooltip: item.name }}>{item.name}</WrapperText>}
                description={<WrapperText ellipsis={{ rows: 2, tooltip: item.type.name }}>{item.type.name}</WrapperText>}
              />
            </Skeleton>
            {isNotReader() && <MenuList children={<EllipsisOutlined />} options={getMenuOptions(item.id)} />}
          </List.Item>
        );
      }}
    />
  );
};
