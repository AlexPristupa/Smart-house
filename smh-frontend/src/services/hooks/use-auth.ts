import { IUserInfo } from 'services/interfaces';

export interface IAuthToken {
  accessToken: string;
  refreshToken: string;
}

export interface IAuthResponse extends IAuthToken {
  userInfo: IUserInfo;
}

export const useAuth = () => {
  const setAuthToken = ({ accessToken, refreshToken }: IAuthToken) => {
    localStorage.setItem('accessToken', accessToken);
    localStorage.setItem('refreshToken', refreshToken);
  };

  const getAuthToken = (): IAuthToken | undefined => {
    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (accessToken && refreshToken) {
      return {
        accessToken,
        refreshToken,
      };
    }
  };

  return { setAuthToken, getAuthToken };
};

export const useUserRole = () => {
  const setUserRole = ({ roleFeatures }: IUserInfo) => {
    localStorage.setItem('role', roleFeatures.role);
  };

  const getUserRole = (): string | undefined => {
    const role = localStorage.getItem('role');

    if (role) {
      return role;
    }
  };
  return { setUserRole, getUserRole };
};

export const useIsRole = () => {
  const { getUserRole } = useUserRole();
  const isNotReader = (): boolean => {
    return getUserRole() !== 'READER';
  };

  const isRoot = (): boolean => {
    return getUserRole() === 'ROOT';
  };

  return { isNotReader, isRoot };
};
