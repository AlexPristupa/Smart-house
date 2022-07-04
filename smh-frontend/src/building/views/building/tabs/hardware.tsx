import { List, Skeleton, Avatar, Modal } from 'antd';
import { BUILDING_QUERY_KEYS, MENU_HEADER } from 'building/constants';
import { IBuildingUrlParams, IHardware } from 'building/interfaces';
import { useParams } from 'react-router';
import { getFileSrc, IFileContent, optimisticUpdatesDelete, useIsRole } from 'services';
import { IMenuItemProps, MenuList, WrapperText } from 'ui';
import { EllipsisOutlined } from '@ant-design/icons';
import { useDeleteHardware, useGetHardwaresList } from 'building/hooks';
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { useCallback } from 'react';
import { useQueryClient } from 'react-query';
import { useRouter } from 'building/utils';
import { ExclamationCircleOutlined } from '@ant-design/icons';
import { useTranslation } from 'react-i18next';

export const HardwareTab: React.FC = () => {
  const { t } = useTranslation();
  const { isNotReader } = useIsRole();
  const { buildingId } = useParams<IBuildingUrlParams>();
  const queryClient = useQueryClient();
  const { goToEditPage, goToContentPage } = useRouter();

  const { data, isLoading } = useGetHardwaresList(buildingId!);

  const { mutate } = useDeleteHardware({
    onMutate: async val => {
      await queryClient.cancelQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);

      const previousHardware = optimisticUpdatesDelete<IHardware>(val, [BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId!], queryClient);

      return { previousHardware };
    },
    onSuccess: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);
    },
    onError: (context: any) => {
      queryClient.setQueryData([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId], context.previousHardware);
    },
    onSettled: () => {
      queryClient.invalidateQueries([BUILDING_QUERY_KEYS.HARDWARE_LIST, buildingId]);
    },
  });

  const deleteHardware = useCallback(
    (id: number) => {
      mutate(id.toString());
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

  const getDescription = (item: IHardware) => {
    return `${item.model}, ${item.serialNumber}`;
  };

  return (
    <List
      locale={{ emptyText: t('messageSystem.noData') }}
      bordered={false}
      loading={isLoading}
      itemLayout="horizontal"
      dataSource={data?.content}
      renderItem={item => (
        <List.Item onClick={() => goToContentPage(item.id)}>
          <Skeleton avatar title={false} loading={isLoading} active>
            <List.Item.Meta
              avatar={<Avatar src={item.photo && !Array.isArray(item.photo) ? getFileSrc(item.photo as IFileContent, true) : undefined} />}
              title={<WrapperText ellipsis={{ rows: 1, tooltip: item.name }}>{item.name}</WrapperText>}
              description={<WrapperText ellipsis={{ rows: 2, tooltip: getDescription(item) }}>{getDescription(item)}</WrapperText>}
            />
          </Skeleton>
          {isNotReader() && <MenuList children={<EllipsisOutlined />} options={getMenuOptions(item.id)} />}
        </List.Item>
      )}
    />
  );
};
