import { BUILDING_QUERY_KEYS } from 'building/constants';
import { IService, IServiceForm, IServiceType } from 'building/interfaces';
import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import { BASE_REQUEST_PATH, IListData, IServerError, request } from 'services';

type MutateOptions<T = IServiceForm> = UseMutationOptions<IService, IServerError[], T>;

export const useGetServiceData = (serviceId: string) =>
  useQuery<IService, IServerError>([BUILDING_QUERY_KEYS.SERVICE, serviceId], () =>
    request<IService>({ url: `${BASE_REQUEST_PATH.SERVICE}/${serviceId}` }),
  );

export const useGetServiceEditData = (serviceId: number) =>
  useQuery<IService, IServerError, IServiceForm>(
    [BUILDING_QUERY_KEYS.EDIT_SERVICE, `${serviceId}`],
    () => request<IService>({ url: `${BASE_REQUEST_PATH.SERVICE}/${serviceId}` }),
    {
      select: ({ type, ...restService }) => ({
        ...restService,
        serviceWorkType: type.id,
        timeStart: restService.startTime,
        timeFinish: restService.finishTime,
      }),
    },
  );

export const useGetServiceList = (buildingId: string) => {
  const rc = useQuery<IListData<IService>, IServerError>(
    [BUILDING_QUERY_KEYS.SERVICE_LIST, buildingId],
    () => request<IListData<IService>>({ url: `facilities/${buildingId}/service-works` }),
    { staleTime: 0 },
  );
  return rc;
};

export const useGetServiceTypes = () =>
  useQuery<IListData<IServiceType>, IServerError>(BUILDING_QUERY_KEYS.SERVICE, () =>
    request<IListData<IServiceType>>({ url: `${BASE_REQUEST_PATH.SERVICE}/types` }),
  );

export const useAddService = (buildingId: number, options: MutateOptions) =>
  useMutation<IService, IServerError[], IServiceForm>(
    service =>
      request({
        url: `${BASE_REQUEST_PATH.BUILDING}/${buildingId}/${BASE_REQUEST_PATH.SERVICE}`,
        method: 'POST',
        data: service,
      }),
    options,
  );

export const useEditService = (options: MutateOptions) =>
  useMutation<IService, IServerError[], IServiceForm>(
    service =>
      request({
        url: `${BASE_REQUEST_PATH.SERVICE}/${service.id}`,
        method: 'POST',
        data: service,
      }),
    options,
  );

export const useDeleteService = (options?: MutateOptions<number>) =>
  useMutation<IService, IServerError[], number>(
    serviceId => request({ url: `${BASE_REQUEST_PATH.SERVICE}/${serviceId}`, method: 'DELETE' }),
    options,
  );
