export interface IFileContent {
  contentId: string;
  contentLength: number;
  createdAt: string;
  mimeType: string;
  name: string;
}

export interface IFileContentWithUrl extends IFileContent {
  url: string;
}
