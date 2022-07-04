import { List } from 'antd';
import { IService } from 'building/interfaces';
import React from 'react';
import { parseTime } from 'services';
import { useTranslation } from 'react-i18next';

interface IProps {
  data: IService;
}

export const CardServicesXs: React.FC<IProps> = ({ data }) => {
  const { t } = useTranslation();
  if (data) {
    return (
      <List
        itemLayout="vertical"
        dataSource={[data!]}
        renderItem={item => (
          <List.Item style={{ wordBreak: 'break-word' }}>
            <List.Item.Meta title={t('service.card.name')} description={item.name} />
            <List.Item.Meta title={t('service.card.type')} description={item.type.name} />
            <List.Item.Meta
              title={t('service.card.workTime')}
              description={`${parseTime(item.startTime, '{d}.{m}.{y} {h}:{i}')} - ${parseTime(item.finishTime, '{d}.{m}.{y} {h}:{i}')}`}
            />
            <List.Item.Meta
              title={t('service.card.statusTitle')}
              description={
                data.resolution === t('button.resolutionBtn.resolved')
                  ? t('service.form.title.resolved')
                  : t('service.form.title.unresolved')
              }
            />
            <List.Item.Meta title={t('service.card.description')} description={item.description} />
          </List.Item>
        )}
      />
    );
  }

  return null;
};
