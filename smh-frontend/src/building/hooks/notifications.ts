import { BUILDING_QUERY_KEYS } from 'building/constants';
import { INotification } from 'building/interfaces';
import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import { BASE_REQUEST_PATH, IListData, IServerError, request } from 'services';

export const useGetNotificationsList = () =>
  useQuery<IListData<INotification>, IServerError[]>(
    BUILDING_QUERY_KEYS.NOTIFICATION_LIST,
    () => request<IListData<INotification>>({ url: BASE_REQUEST_PATH.NOTIFICATIONS }),
    { staleTime: 0 },
  );

export const usePatchNotification = (options: UseMutationOptions<INotification, IServerError[], string>) =>
  useMutation<INotification, IServerError[], string>(
    serEventId => request({ url: `${BASE_REQUEST_PATH.NOTIFICATIONS}/${serEventId}/read`, method: 'PATCH' }),
    options,
  );
