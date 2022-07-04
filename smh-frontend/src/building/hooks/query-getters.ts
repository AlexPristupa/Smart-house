import { HARDWARES } from 'building/constants';
import { IBuilding, IHardware } from 'building/interfaces';
import { useRouter } from 'building/utils';
import { useQueryClient } from 'react-query';

export const useHardwareData = () => {
  const queryClient = useQueryClient();
  const {
    params: { contentId },
  } = useRouter();

  return queryClient.getQueryData<IHardware>([HARDWARES.HARDWARE, `${contentId}`])!;
};

export const useFacilityData = () => {
  const queryClient = useQueryClient();
  const {
    params: { buildingId },
  } = useRouter();

  return queryClient.getQueryData<IBuilding>(['building', buildingId]);
};

/**
 * @description Получить данные сущности из кэша react-query
 * @returns
 */
export const useEntityData = <T>(keyCash: Array<string>) => {
  const queryClient = useQueryClient();

  return queryClient.getQueryData<T>(keyCash);
};
