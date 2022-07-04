import { Badge, Popover, List, Button } from 'antd';
import { BellOutlined } from '@ant-design/icons';
import { WrapperText } from 'ui';
import styled from 'styled-components';
import { useCallback, useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { IHardware, INotification, IService } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { BUILDING_QUERY_KEYS, BUILDNG_CONTENTS } from 'building/constants';
import { useGetNotificationsList, usePatchNotification } from 'building/hooks';
import { useQueryClient } from 'react-query';

const NotificationsIcon = styled(BellOutlined)`
  font-size: 22px;
`;

const DivNotifications = styled.div`
  margin-top: 7px;
  cursor: pointer;
`;

const DivViewAll = styled.div`
  display: flex;
  justify-content: center;
`;

export const BadgeNotifications: React.FC = () => {
  const queryClient = useQueryClient();
  const { data } = useGetNotificationsList();

  const { mutate } = usePatchNotification({
    onMutate: async () => {
      await queryClient.cancelQueries(BUILDING_QUERY_KEYS.NOTIFICATION_LIST);
    },
    onSuccess: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.NOTIFICATION_LIST);
    },
    onError: error => {
      console.log(error);
    },
    onSettled: () => {
      queryClient.invalidateQueries(BUILDING_QUERY_KEYS.NOTIFICATION_LIST);
    },
  });

  const arrNotifications = useMemo(() => data?.content || [], [data?.content]);

  const [visible, setVisible] = useState(false);
  const [notifications, setNotifications] = useState<INotification[]>([]);
  const [count, setCount] = useState(0);

  useEffect(() => {
    setNotifications([...arrNotifications.filter(item => !item.read)]);
    setCount(arrNotifications.filter(item => !item.read).length);
  }, [arrNotifications]);

  const { t } = useTranslation();

  const { goToContentPage, goToNotifications } = useRouter();

  const patchNotification = useCallback(
    (id: string) => {
      mutate(id);
    },
    [mutate],
  );
  const hide = () => {
    setVisible(false);
  };

  const handleVisibleChange = visible => {
    setVisible(visible);
  };

  const clickNotification = useCallback(
    id => {
      const temp = [...notifications.filter(item => item.id !== id)];
      setNotifications(temp);
      setCount(temp.length);
      const notification = notifications.find(item => item.id === id);
      if (notification && notification.type === 'HARDWARE_EXPIRED') {
        const hardware = notification.payload as IHardware;
        goToContentPage(hardware.id, notification.facilityId.toString(), BUILDNG_CONTENTS.HARDWARE);
      } else if (notification) {
        const service = notification.payload as IService;
        goToContentPage(service.id, notification.facilityId.toString(), BUILDNG_CONTENTS.SERVICE);
      }
      patchNotification(id.toString());
    },
    [goToContentPage, notifications, patchNotification],
  );

  const getNotification = (entity: INotification) => {
    let typeMessage = '';
    if (entity.type === 'HARDWARE_EXPIRED') {
      typeMessage = `Для оборудования ${entity.payload.name}`;
    } else if (entity.type === 'SERVICE_WORK_BEFORE_START') {
      typeMessage = `Начало сервисной работы ${entity.payload.name}`;
    } else if (entity.type === 'SERVICE_WORK_FINISHED') {
      typeMessage = `Окончание сервисной работы ${entity.payload.name}`;
    }

    return `${typeMessage}`;
  };

  const content = () => (
    <>
      <List
        style={{ width: '300px' }}
        locale={{ emptyText: t('messageSystem.noNew') }}
        bordered={false}
        itemLayout="horizontal"
        dataSource={notifications}
        renderItem={item => (
          <List.Item onClick={() => clickNotification(item.id)}>
            <List.Item.Meta
              title={<WrapperText ellipsis={{ rows: 1, tooltip: getNotification(item) }}>{getNotification(item)}</WrapperText>}
            />
          </List.Item>
        )}
      />
      <DivViewAll>
        <Button
          type="primary"
          onClick={() => {
            hide();
            goToNotifications();
          }}
        >
          {t('button.viewAll')}
        </Button>
      </DivViewAll>
    </>
  );

  return (
    <DivNotifications>
      <Popover content={content} title={t('notifications.title')} trigger="click" visible={visible} onVisibleChange={handleVisibleChange}>
        <Badge count={count} overflowCount={9}>
          <NotificationsIcon />
        </Badge>
      </Popover>
    </DivNotifications>
  );
};
