import { IAuthResponse, IAuthToken } from 'services';
import { IRequestProps, IServerError } from './interfaces';
import { history } from './history';
import i18n from 'i18n';
import { message } from 'antd';

const getAuthHeader = (token?: string) => ({ Authorization: `Bearer ${token || localStorage.getItem('accessToken')}` });

export const baseRequest = async <T, K = string, E = IServerError[]>(props: IRequestProps<K>): Promise<T> => {
  const {
    url,
    method = 'GET',
    urlPrefix = '/api/v1/',
    noPrefix,
    headers: requestHeaders,
    data,
    jsonServer,
    isRetry,
    isFileRequest,
  } = props;

  const requestUrl = noPrefix ? url : urlPrefix + url;

  const res = await fetch(jsonServer ? `http://localhost:5000/api/${url}` : requestUrl, {
    method,
    body: data as any,
    headers: new Headers({
      ...requestHeaders,
    }),
  });

  if (res.status === 204) {
    const resp: T = (await res.text()) as unknown as T;
    console.log('res.status === 204', resp);
    return resp;
  }

  if (res.ok) {
    const okRes: T = isFileRequest ? await res.blob() : await res.json();
    return okRes;
  }

  if (res.status === 401 && requestHeaders?.['Authorization']) {
    const currentRefreshToken = localStorage.getItem('refreshToken');

    if (currentRefreshToken && !isRetry) {
      const { refreshToken, accessToken } = await authRequest<IAuthResponse, Omit<IAuthToken, 'accessToken'>>({
        url: 'auth/refresh',
        data: { refreshToken: currentRefreshToken },
        method: 'POST',
      });

      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);

      return baseRequest<T, K, E>({ ...props, headers: { ...props.headers, ...getAuthHeader(accessToken) }, isRetry: true });
    }

    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');

    history.push('/');
  }

  if (res.status === 401) {
    throw i18n.t('rules.wrongPassword');
  }

  const errRes: E = await res.json();

  if (res.status === 400) {
    throw errRes;
  } else if (res.status === 500) {
    message.error(i18n.t('messageSystem.error'));
    throw i18n.t('messageSystem.error');
  }

  throw i18n.t<any>(`rules.${errRes[0].message}`);
};

export const authRequest = <T, K = Object>({ headers: requestHeaders, data, ...restProps }: IRequestProps<K>): Promise<T> =>
  baseRequest<T>({
    ...restProps,
    data: JSON.stringify(data),
    headers: { ...requestHeaders, 'Content-Type': 'application/json' },
  });

export const request = <T, K = Object>({ headers: requestHeaders, data, ...restProps }: IRequestProps<K>): Promise<T> =>
  baseRequest<T>({
    ...restProps,
    data: JSON.stringify(data),
    headers: { ...requestHeaders, 'Content-Type': 'application/json', Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
  });

export const formDataRequest = <T, K = FormData>({ headers: requestHeaders, ...restProps }: IRequestProps<K>): Promise<T> =>
  baseRequest<T, K>({
    ...restProps,
    headers: { ...requestHeaders, Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
  });

export const fileRequest = <T, K = FormData>({ headers: requestHeaders, ...restProps }: IRequestProps<K>): Promise<T> =>
  baseRequest<T, K>({
    ...restProps,
    isFileRequest: true,
    headers: { ...requestHeaders, Authorization: `Bearer ${localStorage.getItem('accessToken')}` },
  });
