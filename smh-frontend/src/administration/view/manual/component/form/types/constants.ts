import { IServiceType } from 'building/interfaces';

enum SERVICE_KEYS {
  NAME,
}

export const SERVICE_TYPE_FORM_FIELDS: Record<keyof typeof SERVICE_KEYS, keyof Partial<IServiceType>> = {
  NAME: 'name',
};
