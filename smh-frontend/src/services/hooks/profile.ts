import { useQuery } from 'react-query';
import { request } from '../request';
import { IProfile, IServerError } from '../interfaces';
import { QUERY_KEYS } from 'services';

export const useGetProfileData = () =>
  useQuery<IProfile, IServerError>({
    queryKey: QUERY_KEYS.PROFILE,
    queryFn: () => request<IProfile>({ url: 'profile' }),
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
