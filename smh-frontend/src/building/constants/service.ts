import { NotificationOutlined, PhoneOutlined, LockOutlined } from '@ant-design/icons';

export enum SERVICE_TYPE {
  PHONE_INSTALLATION = 'PHONE_INSTALLATION',
  STORAGE_LEASE = 'STORAGE_LEASE',
  AD = 'AD',
}

export const SERVICE_TYPE_ICONS: Record<keyof typeof SERVICE_TYPE, typeof NotificationOutlined> = {
  AD: NotificationOutlined,
  PHONE_INSTALLATION: PhoneOutlined,
  STORAGE_LEASE: LockOutlined,
};
