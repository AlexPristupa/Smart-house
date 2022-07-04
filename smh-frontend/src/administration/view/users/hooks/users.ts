import { useMutation, UseMutationOptions, useQuery } from 'react-query';
import { BASE_REQUEST_PATH, IListData, IServerError, request } from 'services';
import { IUser, IUserObject } from '../interfaces';
import { ADMIN_QUERY_KEYS } from 'administration/constants';

type MutateUserOptions<T = IUserObject> = UseMutationOptions<IUserObject, IServerError[], T>;

export const useGetUserList = () =>
  useQuery<IListData<IUser>, IServerError>(
    ADMIN_QUERY_KEYS.USER_LIST,
    () => request<IListData<IUser>>({ url: `${BASE_REQUEST_PATH.USER_LIST}` }),
    { staleTime: 0 },
  );

export const useGetUserData = (userId: number) =>
  useQuery<IUser, IServerError, IUser>([ADMIN_QUERY_KEYS.USER, `${userId}`], () =>
    request<IUser>({ url: `${BASE_REQUEST_PATH.USER_LIST}/${userId}` }),
  );

export const useEditUser = (userId: number, options: MutateUserOptions) =>
  useMutation<IUserObject, IServerError[], IUserObject>(
    user =>
      request({
        url: `${BASE_REQUEST_PATH.USER_LIST}/${userId}`,
        method: 'POST',
        data: user,
      }),
    options,
  );

export const useDeleteUser = (options?: MutateUserOptions<number>) =>
  useMutation<IUserObject, IServerError[], number>(
    userId => request({ url: `${BASE_REQUEST_PATH.USER_LIST}/${userId}`, method: 'DELETE' }),
    options,
  );
