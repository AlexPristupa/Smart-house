import { IFileContent } from './file';

export enum USER_ROLE {
  ADMIN = 'ADMIN',
  READER = 'READER',
  ROOT = 'ROOT',
  WRITER = 'WRITER',
}

export interface IProfile {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
  birthDate?: string;
  patronymic?: string;
  photo: IFileContent;
  role: USER_ROLE;
}
