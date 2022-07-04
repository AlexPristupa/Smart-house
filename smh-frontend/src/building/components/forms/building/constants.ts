import { IBuilding } from 'building/interfaces';

enum BUILDING_KEYS {
  NAME,
  PHOTO,
  ADDRESS,
  DESCRIPTION,
}

export const BUILDING_FORM_FIELDS: Record<keyof typeof BUILDING_KEYS, keyof Omit<IBuilding, 'id'>> = {
  NAME: 'name',
  PHOTO: 'photo',
  ADDRESS: 'address',
  DESCRIPTION: 'description',
};
