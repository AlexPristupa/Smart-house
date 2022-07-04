import { IUser } from 'services';

export interface IRegister extends Omit<IUser, 'id'> {
  password: string;
  passwordRepeat: string;
}

export type RegisterRequest = Omit<IRegister, 'passwordRepeat'>;
