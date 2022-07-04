import { FormInstance } from 'antd';
import { IMenuListProps } from 'ui';
import { IServerError } from '../../../services';

export interface IFormProps<T> {
  form: FormInstance<T>;
  initialValues?: T;
  onCancel: (form: FormInstance<T>) => void;
  onFinish?: (values: T) => void;
  menuOptionsProps?: IMenuListProps;
  isLoading: boolean;
  title?: string;
  error?: IServerError[] | undefined;
}
