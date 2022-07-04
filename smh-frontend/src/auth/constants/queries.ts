import { IQueryInfo } from 'services';

enum QUERY_KEYS {
  LOGIN,
  REGISTER,
}

export const AUTH_QUERIES: Record<keyof typeof QUERY_KEYS, IQueryInfo> = {
  LOGIN: { KEY: 'login', URL: 'auth/login' },
  REGISTER: { KEY: 'registration', URL: 'registration' },
};
