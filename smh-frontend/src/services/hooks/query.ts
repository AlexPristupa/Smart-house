import { QueryKey, useQueryClient } from 'react-query';

export const useGetQueryState = <T>(key: QueryKey) => {
  const queryClient = useQueryClient();

  console.log(queryClient.getQueryState<T>(key));

  return queryClient.getQueryCache;
};
