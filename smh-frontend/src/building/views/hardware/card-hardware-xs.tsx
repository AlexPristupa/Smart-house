import { List } from 'antd';
import { BUILDING_QUERY_KEYS } from 'building/constants';
import { useEntityData } from 'building/hooks';
import { IHardware } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { getFileSrc, IFileContent, parseTime } from 'services';
import { CardHardwareImage } from 'ui';
import { useTranslation } from 'react-i18next';

export const CardHardwareXs: React.FC = () => {
  const {
    params: { contentId },
  } = useRouter();

  const data = useEntityData<IHardware>([BUILDING_QUERY_KEYS.HARDWARE, `${contentId}`])!;

  const { t } = useTranslation();

  return (
    <List
      itemLayout="vertical"
      dataSource={[data]}
      renderItem={item => (
        <List.Item>
          <CardHardwareImage
            image={item && item.photo && !Array.isArray(item.photo) ? getFileSrc(item.photo as IFileContent, true) : undefined}
          />
          <List.Item.Meta title={t('hardware.card.nameBuilding')} description={item.name} style={{ overflowWrap: 'break-word' }} />
          <List.Item.Meta title={t('hardware.card.model')} description={item.model} />
          <List.Item.Meta title={t('hardware.card.serialNumber')} description={item.serialNumber} />
          <List.Item.Meta title={t('hardware.card.installedAt')} description={parseTime(item?.installedAt, '{d}.{m}.{y}')} />
          <List.Item.Meta title={t('hardware.card.expiresAt')} description={parseTime(item?.expiresAt, '{d}.{m}.{y}')} />
          <List.Item.Meta title={t('hardware.card.installer')} description={item.installer} style={{ overflowWrap: 'break-word' }} />
        </List.Item>
      )}
    />
  );
};
