import { IProfile } from 'services';

enum BUILDING_KEYS {
  LAST_NAME,
  FIRST_NAME,
  PATRONYMIC,
  BIRTH_DATE,
  EMAIL,
  PHOTO,
  ROLE,
}

export const PROFILE_FORM_FIELDS: Record<keyof typeof BUILDING_KEYS, keyof Omit<IProfile, 'id'>> = {
  LAST_NAME: 'lastName',
  FIRST_NAME: 'firstName',
  PATRONYMIC: 'patronymic',
  PHOTO: 'photo',
  EMAIL: 'email',
  BIRTH_DATE: 'birthDate',
  ROLE: 'role',
};
