export enum METHODS {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
  PATCH = 'PATCH',
}

export interface IRequestProps<T> {
  url: string;
  method?: keyof typeof METHODS;
  headers?: HeadersInit;
  data?: T;
  urlPrefix?: string;
  noPrefix?: boolean;
  jsonServer?: boolean;
  isRetry?: boolean;
  isFileRequest?: boolean;
}

export interface IServerError {
  code?: number;
  message: string;
  fieldName: string;
}
