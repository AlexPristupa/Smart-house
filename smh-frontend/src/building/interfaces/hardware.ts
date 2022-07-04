import { UploadFile } from 'antd/lib/upload/interface';
import { IFileContent } from 'services';

export interface IHardwareList {
  id: number;
  list: IHardware[];
}

export interface IHardware {
  description: string;
  expiresAt: string;
  id: number;
  images: IFileContent[] | UploadFile[];
  installedAt: string;
  installer: string;
  model: string;
  name: string;
  photo: IFileContent[] | UploadFile[] | IFileContent | string | null;
  serialNumber: string;
}

export interface IHardwareObject extends IHardware {
  entityId?: string;
  action?: string | 'edit' | 'new';
  editImage?: boolean;
  editPhoto?: boolean;
}
