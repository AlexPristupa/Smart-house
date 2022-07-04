import { IRegister } from 'auth/interfaces';

enum REGISTER_KEYS {
  FIRST_NAME,
  LAST_NAME,
  PATRONYMIC,
  EMAIL,
  PASSWORD,
  PASSWORD_REPEAT,
}

export const REGISTER_FIELDS: Record<keyof typeof REGISTER_KEYS, keyof IRegister> = {
  FIRST_NAME: 'firstName',
  LAST_NAME: 'lastName',
  PATRONYMIC: 'patronymic',
  EMAIL: 'email',
  PASSWORD: 'password',
  PASSWORD_REPEAT: 'passwordRepeat',
};
