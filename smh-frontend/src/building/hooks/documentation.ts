import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IDocumentation } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import { BASE_REQUEST_PATH, fileRequest, formDataRequest, IFileContent, IListData, IServerError, request } from 'services';
import fileDownload from 'js-file-download';

export const useGetDocumentationList = (facilityId: string) =>
  useQuery<IListData<IDocumentation>, IServerError>([BUILDING_QUERY_KEYS.DOCUMENTATION_LIST, facilityId], () =>
    request<IListData<IDocumentation>>({ url: `${BASE_REQUEST_PATH.BUILDING}/${facilityId}/${BASE_REQUEST_PATH.DOCUMENTATION}` }),
  );

export const useAddDocumentationFiles = (options?: UseMutationOptions<IListData<IDocumentation>, IServerError, File[]>) => {
  const {
    params: { buildingId },
  } = useRouter();

  return useMutation<IListData<IDocumentation>, IServerError, File[]>(files => {
    const formData = new FormData();

    files.forEach(file => {
      formData.append('files', file);
    });

    return formDataRequest({
      url: `${BASE_REQUEST_PATH.BUILDING}/${buildingId}/${BASE_REQUEST_PATH.DOCUMENTATION}`,
      method: 'POST',
      data: formData,
    });
  }, options);
};

export const useEditDocumentationFile = (docId: number, options?: UseMutationOptions<IDocumentation, IServerError, string>) => {
  return useMutation<IDocumentation, IServerError, string>(newName => {
    const formData = new FormData();

    formData.append('newName', newName);

    return formDataRequest({
      url: `${BASE_REQUEST_PATH.DOCUMENTATION}/${docId}`,
      method: 'PATCH',
      data: formData,
    });
  }, options);
};

export const useDeleteDocumentation = (options: UseMutationOptions<IListData<IDocumentation>, IServerError, string>) =>
  useMutation<IListData<IDocumentation>, IServerError, string>(
    docId => request({ url: `${BASE_REQUEST_PATH.DOCUMENTATION}/${docId}`, method: 'DELETE' }),
    options,
  );

export const useDownloadFile = () =>
  useMutation<Blob, IServerError, IFileContent>(file => fileRequest({ url: `content/${file.contentId}` }), {
    onSuccess: (data, context) => {
      fileDownload(data, context.name);
    },
  });
