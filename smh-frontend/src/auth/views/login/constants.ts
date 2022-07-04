import { ILogin } from 'auth/interfaces';

enum LOGIN_KEYS {
  USERNAME,
  PASSWORD,
}

export const LOGIN_FIELDS: Record<keyof typeof LOGIN_KEYS, keyof ILogin> = {
  USERNAME: 'username',
  PASSWORD: 'password',
};
