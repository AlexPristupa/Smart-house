export type EnumKey<T> = keyof T;

export interface IListSort {
  unsorted: boolean;
  sorted: boolean;
  empty: boolean;
}

export interface IPageable {
  offset: number;
  pageNumber: number;
  pageSize: number;
  sort: IListSort;
}

export interface IListData<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElemens: number;
  pageable: IPageable;
  size: number;
  sort: IListSort;
  totalElements: number;
  totalPages: number;
}

/** Тип для модификации интерфейсов (аналог override) */
export type Modify<T, R> = Omit<T, keyof R> & R;
