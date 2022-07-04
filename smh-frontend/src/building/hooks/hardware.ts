import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IHardware, IHardwareObject } from 'building/interfaces';
import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import {
  BASE_REQUEST_PATH,
  filterUploadFilesFromEntity,
  formDataRequest,
  getEntityImagePhoto,
  IListData,
  IServerError,
  METHODS,
  request,
} from 'services';

export const useGetHardwareData = (hardwareId: string) =>
  useQuery<IHardware, IServerError>([BUILDING_QUERY_KEYS.HARDWARE, hardwareId], () =>
    request<IHardware>({ url: `${BASE_REQUEST_PATH.HARDWARE}/${hardwareId}` }),
  );

export const useGetHardwaresList = (facilityId: string) =>
  useQuery<IListData<IHardware>, IServerError>([BUILDING_QUERY_KEYS.HARDWARE, 'list', facilityId], () =>
    request<IListData<IHardware>>({ url: `${BASE_REQUEST_PATH.BUILDING}/${facilityId}/${BASE_REQUEST_PATH.HARDWARE}` }),
  );

const sendMutateHardwareRequest = (hardwareObject: IHardwareObject, method: keyof typeof METHODS): Promise<IHardware> => {
  const formData = new FormData();

  const data = getEntityImagePhoto(hardwareObject, formData);

  formData.append('hardware', new Blob([JSON.stringify(filterUploadFilesFromEntity(data))], { type: 'application/json' }));

  if (hardwareObject.action === 'new') {
    return formDataRequest({
      url: `${BASE_REQUEST_PATH.BUILDING}/${hardwareObject.entityId}/${BASE_REQUEST_PATH.HARDWARE}`,
      data: formData,
      method,
    });
  } else {
    return formDataRequest({ url: `${BASE_REQUEST_PATH.HARDWARE}/${hardwareObject.entityId}`, data: formData, method });
  }
};

export const useAddHardware = (options: UseMutationOptions<IHardwareObject, IServerError[], IHardwareObject>) =>
  useMutation<IHardwareObject, IServerError[], IHardwareObject>(hardware => sendMutateHardwareRequest(hardware, 'POST'), options);

export const useEditHardware = (options: UseMutationOptions<IHardwareObject, IServerError, IHardwareObject>) =>
  useMutation<IHardware, IServerError, IHardware>(hardware => sendMutateHardwareRequest(hardware, 'POST'), options);

export const useDeleteHardware = (options: UseMutationOptions<IHardware, IServerError, string>) =>
  useMutation<IHardware, IServerError, string>(
    hardwareId => request({ url: `${BASE_REQUEST_PATH.HARDWARE}/${hardwareId}`, method: 'DELETE' }),
    options,
  );
