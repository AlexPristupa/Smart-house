import { QueryClient } from 'react-query';
import { IListData } from 'services';

export const optimisticUpdatesAdd = <T>(entity: T, keyCach: string | string[], queryClient: QueryClient): IListData<T> => {
  const previousData = queryClient.getQueryData<IListData<T>>(keyCach);

  queryClient.setQueryData<IListData<T>>(keyCach, old => {
    const entitysOld = old as unknown as IListData<T>;

    if (entitysOld.content) {
      entitysOld.content = [...entitysOld.content, entity];
    } else {
      entitysOld.content = [entity];
    }

    return entitysOld;
  });

  return previousData!;
};

export const optimisticUpdatesEdit = <T>(
  entity: T,
  entityId: string,
  keyCach: string | string[],
  queryClient: QueryClient,
): IListData<T> => {
  const previousData = queryClient.getQueryData<IListData<T>>(keyCach);

  queryClient.setQueryData<IListData<T>>(keyCach, old => {
    const entitysOld = old as unknown as IListData<T>;

    if (entitysOld.content) {
      let item = entitysOld.content.find((item: any) => item.id?.toString() === entityId);
      Object.assign(item, entity);
    }

    return entitysOld;
  });

  return previousData!;
};

export const optimisticUpdatesDelete = <T>(entityId: string, keyCach: string | string[], queryClient: QueryClient): IListData<T> => {
  const previousData = queryClient.getQueryData(keyCach) as IListData<T>;

  const newData = previousData ? previousData.content.filter((item: any) => item.id.toString() !== entityId) : [];

  queryClient.setQueryData<Array<T>>(keyCach, () => [...newData]);

  return previousData!;
};
