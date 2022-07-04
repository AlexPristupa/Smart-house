import { IFileContent } from 'services';
import { UploadFile } from 'antd/lib/upload/interface';

export interface IUser {
  id: number;
  firstName: string;
  lastName: string;
  patronymic: string;
  email: string;
  birthDate?: string;
  role: string;
  photo?: IFileContent[] | UploadFile[] | IFileContent | string;
}

export interface IUserObject extends IUser {
  entityId?: string;
  action?: string | 'edit';
  editPhoto?: boolean;
}
