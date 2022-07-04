import { IUser } from '../../interfaces';

enum USER_KEYS {
  FIRST_NAME,
  LAST_NAME,
  PATRONYMIC,
  EMAIL,
  ROLE,
  PHOTO,
}

export const USER_FORM_FIELDS: Record<keyof typeof USER_KEYS, keyof Omit<IUser, 'id'>> = {
  FIRST_NAME: 'firstName',
  LAST_NAME: 'lastName',
  PATRONYMIC: 'patronymic',
  EMAIL: 'email',
  ROLE: 'role',
  PHOTO: 'photo',
};
