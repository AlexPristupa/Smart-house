import { IHardware } from 'building/interfaces';

enum HARDWARE_KEYS {
  PHOTO,
  DESCRIPTION,
  EXPIERSAT,
  INSTALLERAT,
  INSTALLER,
  MODEL,
  NAME,
  SERIALNUMBER,
}

export const HARDWARE_FORM_FIELDS: Record<keyof typeof HARDWARE_KEYS, keyof Omit<IHardware, 'id'>> = {
  PHOTO: 'photo',
  DESCRIPTION: 'description',
  EXPIERSAT: 'expiresAt',
  INSTALLERAT: 'installedAt',
  INSTALLER: 'installer',
  MODEL: 'model',
  NAME: 'name',
  SERIALNUMBER: 'serialNumber',
};
