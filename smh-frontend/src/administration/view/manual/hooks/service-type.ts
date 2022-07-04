import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import { BASE_REQUEST_PATH, IListData, IServerError, request } from 'services';
import { IServiceType } from 'building/interfaces';
import { BUILDING_QUERY_KEYS } from 'building/constants';

type MutateServiceTypeOptions<T = IServiceType> = UseMutationOptions<IServiceType, IServerError[], T>;

export const useGetServiceTypes = () =>
  useQuery<IListData<IServiceType>, IServerError>(BUILDING_QUERY_KEYS.SERVICE, () =>
    request<IListData<IServiceType>>({ url: `${BASE_REQUEST_PATH.SERVICE}/types` }),
  );

export const useAddServiceType = (options: MutateServiceTypeOptions) =>
  useMutation<IServiceType, IServerError[], IServiceType>(
    serviceType =>
      request({
        url: `${BASE_REQUEST_PATH.SERVICE}/${BASE_REQUEST_PATH.TYPES}`,
        method: 'POST',
        data: serviceType,
      }),
    options,
  );

export const useDeleteServiceType = (options: MutateServiceTypeOptions) =>
  useMutation<IServiceType, IServerError[], IServiceType>(
    serviceTypeId =>
      request({
        url: `${BASE_REQUEST_PATH.SERVICE}/${BASE_REQUEST_PATH.TYPES}/${serviceTypeId.id}`,
        method: 'DELETE',
      }),
    options,
  );

export const useEditServiceType = (serviceTypeId: number, options: MutateServiceTypeOptions) =>
  useMutation<IServiceType, IServerError[], IServiceType>(
    serviceType =>
      request({
        url: `${BASE_REQUEST_PATH.SERVICE}/${BASE_REQUEST_PATH.TYPES}/${serviceTypeId}`,
        method: 'POST',
        data: serviceType,
      }),
    options,
  );
