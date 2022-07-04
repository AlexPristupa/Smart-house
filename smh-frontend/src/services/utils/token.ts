import { IAuthToken } from '../hooks';

export const getAccessToken = (): string | null => localStorage.getItem('accessToken');

export const getRefreshToken = (): string | null => localStorage.getItem('refreshToken');

export const getIsUserAuthenticated = () => Boolean(getAccessToken() && getRefreshToken());

export const setAuthToken = ({ accessToken, refreshToken }: IAuthToken) => {
  localStorage.setItem('accessToken', accessToken);
  localStorage.setItem('refreshToken', refreshToken);
};

export const clearAuthToken = () => {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('refreshToken');
};
