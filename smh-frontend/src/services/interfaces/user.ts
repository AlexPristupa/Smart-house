import { USER_STATUS } from 'services';

export interface IUser {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  patronymic?: string;
}

export interface IUserInfo {
  email: string;
  // TODO: дополнить тип по готовности.
  roleFeatures: {
    firstName: string;
    lastName: string;
    role: string;
  };
  status: USER_STATUS;
}
