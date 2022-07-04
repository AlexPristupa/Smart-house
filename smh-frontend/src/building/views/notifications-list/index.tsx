import { List } from 'antd';

import { Header } from 'building/components';
import { PageContainer, WrapperText } from 'ui';
import { useGetNotificationsList } from 'building/hooks';

import { useTranslation } from 'react-i18next';
import { useRouter } from 'building/utils';
import { IHardware, INotification, IService } from 'building/interfaces';
import { BUILDNG_CONTENTS } from 'building/constants';
import { parseTime } from 'services';
//import { parseTime } from 'services';

export const NotificationsList: React.FC = () => {
  //const queryClient = useQueryClient();
  const { goToContentPage } = useRouter();
  const { t } = useTranslation();

  const { data, isLoading } = useGetNotificationsList();
  // const { md } = useBreakpoint();

  const goTo = (entity: INotification) => {
    if (entity && entity.type === 'HARDWARE_EXPIRED') {
      goToContentPage(entity.payload.id, entity.facilityId.toString(), BUILDNG_CONTENTS.HARDWARE);
    } else {
      goToContentPage(entity.payload.id, entity.facilityId.toString(), BUILDNG_CONTENTS.SERVICE);
    }
  };

  // TODO : сделать общей функцией для вывода уведомления
  const getNotification = (entity: INotification) => {
    let typeMessage = '';
    if (entity.type === 'HARDWARE_EXPIRED') {
      typeMessage = `Срок годности оборудования ${entity.payload.name}, серийный номер ${
        (entity.payload as unknown as IHardware).serialNumber
      } истекает ${parseTime((entity.payload as unknown as IHardware).expiresAt, '{d}.{m}.{y}')}`;
    } else if (entity.type === 'SERVICE_WORK_BEFORE_START') {
      typeMessage = `Начало сервисной работы ${entity.payload.name} в ${parseTime(
        (entity.payload as unknown as IService).startTime,
        '{d}.{m}.{y}, {h}:{i}',
      )}`;
    } else if (entity.type === 'SERVICE_WORK_FINISHED') {
      typeMessage = `Окончание сервисной работы ${entity.payload.name} в ${parseTime(
        (entity.payload as unknown as IService).finishTime,
        '{d}.{m}.{y}, {h}:{i}',
      )}`;
    }

    return `${typeMessage}`;
  };

  const isAvalible = Boolean(data); //   || error?.code !== 404;

  return (
    <>
      <Header title={t('notifications.title')} />
      <PageContainer avalibility={isAvalible}>
        <List
          locale={{ emptyText: t('messageSystem.noNew') }}
          loading={isLoading}
          bordered={false}
          itemLayout="horizontal"
          dataSource={data?.content}
          renderItem={item => (
            <List.Item onClick={() => item && goTo(item as INotification)}>
              <List.Item.Meta
                title={
                  <WrapperText ellipsis={{ rows: 1, tooltip: getNotification(item as INotification) }}>
                    {getNotification(item as INotification)}
                  </WrapperText>
                }
              />
            </List.Item>
          )}
        />
      </PageContainer>
    </>
  );
};
