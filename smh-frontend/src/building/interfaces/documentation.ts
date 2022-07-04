import { IFileContent } from 'services';

export interface IDocumentation {
  id: number;
  name: string;
  nodeType: 'FILE' | 'FOLDER';
  file: IFileContent;
}
