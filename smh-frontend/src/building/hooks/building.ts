import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IBuilding, IBuildingObject } from 'building/interfaces';
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

export const useGetBuildingData = (buildingId: string) =>
  useQuery<IBuilding, IServerError[]>([BUILDING_QUERY_KEYS.BUILDING, buildingId], () =>
    request<IBuilding>({ url: `${BASE_REQUEST_PATH.BUILDING}/${buildingId}` }),
  );

export const useGetBuildingsList = () =>
  useQuery<IListData<IBuilding>, IServerError[]>(
    BUILDING_QUERY_KEYS.BUILDING_LIST,
    () => request<IListData<IBuilding>>({ url: BASE_REQUEST_PATH.BUILDING }),
    { staleTime: 0 },
  );

const sendMutateBuildingRequest = (facility: IBuildingObject, method: keyof typeof METHODS): Promise<IBuilding> => {
  const formData = new FormData();

  const data = getEntityImagePhoto(facility, formData);

  formData.append('facility', new Blob([JSON.stringify(filterUploadFilesFromEntity(data))], { type: 'application/json' }));

  if (facility.action === 'new') {
    return formDataRequest({ url: BASE_REQUEST_PATH.BUILDING, data: formData, method });
  } else {
    return formDataRequest({ url: `${BASE_REQUEST_PATH.BUILDING}/${facility.entityId}`, data: formData, method });
  }
};

export const useAddBuilding = (options: UseMutationOptions<IBuildingObject, IServerError[], IBuildingObject>) =>
  useMutation<IBuildingObject, IServerError[], IBuildingObject>(facility => sendMutateBuildingRequest(facility, 'POST'), options);

export const useEditBuilding = (options: UseMutationOptions<IBuildingObject, IServerError[], IBuildingObject>) =>
  useMutation<IBuildingObject, IServerError[], IBuildingObject>(facility => sendMutateBuildingRequest(facility, 'POST'), options);

export const useDeleteBuilding = (options: UseMutationOptions<IBuilding, IServerError[], string>) =>
  useMutation<IBuilding, IServerError[], string>(
    buildingId => request({ url: `${BASE_REQUEST_PATH.BUILDING}/${buildingId}`, method: 'DELETE' }),
    options,
  );
