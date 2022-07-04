import { IFileContent } from 'services';
import { UploadFile } from 'antd/lib/upload/interface';

export interface IBuilding {
  id: number;
  name: string;
  description?: string;
  address: string;
  photo?: IFileContent[] | UploadFile[] | IFileContent;
  images?: IFileContent[] | UploadFile[];
}

export interface IBuildingObject extends IBuilding {
  entityId?: string;
  action?: string | 'edit' | 'new';
  editImage?: boolean;
  editPhoto?: boolean;
}
