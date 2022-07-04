import { UploadFile } from 'antd/lib/upload/interface';
import { IBuildingObject, IHardwareObject } from 'building/interfaces';
import { getAccessToken, IFileContent, IFileContentWithUrl } from 'services';
import { IUserObject } from '../../administration/view/users/interfaces';

export const getBase64 = file => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
};

export const transformFileContentsToUploadFiles = (fileContents: IFileContentWithUrl[]): UploadFile[] =>
  fileContents.map(({ contentId, contentLength, name, url }) => ({ uid: contentId, size: contentLength, name, url }));

export const appendUploadFilesToFormData = <T>(
  formData: FormData,
  formDataName: keyof T,
  fileList: IFileContent[] | UploadFile[] | IFileContent | undefined,
) => {
  if (Array.isArray(fileList)) {
    const files = (fileList as UploadFile[])?.filter(image => Boolean(image.originFileObj));

    if (files) {
      files.forEach(image => formData.append(formDataName as string, image.originFileObj as File));
    }
  }
};

/**
 * Функция, фильтрующая новые добавленные файлы в форму для разделения данных при отправка через formData.
 * @param entity Объект для филтрации от новых файлов.
 */
export const filterUploadFilesFromEntity = <T>(entity: T) =>
  Object.entries(entity).reduce<T>((acc, [key, value]) => {
    if (Array.isArray(value) && value.every(file => file.contentId || file.originFileObj || file.uid)) {
      const val = (value as IFileContent[]).filter(file => Boolean(file.contentId));

      return { ...acc, [key]: val.length > 0 ? val : undefined };
    }

    return { ...acc, [key]: value };
  }, {} as T);

export const getFileSrc = (photo: string | IFileContent, isPreview?: boolean) => {
  const previewFlag = isPreview ? '/preview' : '';
  const contentId = typeof photo === 'string' ? photo : photo.contentId;
  return `/api/v1/content/${contentId}${previewFlag}?token=${getAccessToken()}`;
};

export const getEntityUserImagePhoto = (entity: IUserObject, formData: FormData) => {
  const { entityId, action, editPhoto, ...entitys } = entity;
  // const formData = new FormData();

  if (editPhoto) {
    appendUploadFilesToFormData(formData, 'photo', entitys.photo as IFileContent[]);
  } else {
    const photo = entity.photo as IFileContent;

    entitys.photo = photo ? photo.contentId : undefined;
  }

  return entitys;
};

export const getEntityImagePhoto = <T extends IHardwareObject | IBuildingObject>(entity: T, formData: FormData) => {
  const { entityId, action, editImage, editPhoto, ...entitys } = entity;
  // const formData = new FormData();

  if (editPhoto) {
    appendUploadFilesToFormData<T>(formData, 'photo', entitys.photo as IFileContent[]);
  } else {
    const photo = entity.photo as IFileContent;
    entitys.photo = photo ? photo.contentId : undefined;
  }

  if (editImage) {
    appendUploadFilesToFormData<T>(formData, 'images', entity.images);
  } else {
    entitys.images = entity.images?.map(item => item.contentId);
  }

  return entitys;
};
