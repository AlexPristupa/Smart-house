import { useQuery } from 'react-query';
import { IAuthResponse, IServerError, authRequest } from 'services';
import { BUILDING_QUERY_KEYS } from '../../building/constants';
import { ILogin } from '../interfaces';
import { AUTH_QUERIES } from '../constants';

export const useAuthLogin = (data: ILogin) =>
  useQuery<IAuthResponse, IServerError, ILogin>(BUILDING_QUERY_KEYS.BUILDING_LIST, data =>
    authRequest({ url: AUTH_QUERIES.LOGIN.URL, method: 'POST', data }),
  );
