import { UploadFile } from 'antd/lib/upload/interface';
import { useQueries } from 'react-query';
import { fileRequest, IFileContent } from 'services';

interface IGetContentFilesConfig {
  name: string;
  fileContents?: IFileContent[];
  isPreview?: boolean;
}

export const useGetContentFiles = ({ name, fileContents = [], isPreview }: IGetContentFilesConfig): UploadFile[] | undefined => {
  const files = useQueries(
    fileContents.map(({ contentId, contentLength, name }) => {
      return {
        queryKey: [name, contentId],
        queryFn: () => fileRequest({ url: `content/${contentId}${isPreview ? '/preview' : ''}` }),
        select: rawFile => ({ uid: contentId, size: contentLength, fileName: name, name, url: URL.createObjectURL(rawFile) } as UploadFile),
      };
    }),
  );

  const filesData = files.map(file => file.data as UploadFile);

  if (filesData.every(file => Boolean(file))) {
    return filesData;
  }
};
