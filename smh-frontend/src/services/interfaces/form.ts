/**
 * @description Общий интерфейс пропсов формы
 */

export enum NAME_INPUT {
  INPUT = 'input',
  DATE_PICKER = 'datepicker',
  SELECT = 'select',
  RANGE_PICKER = 'rangepicker',
  CHECKBOX = 'checkbox',
  RADIO_GROUP = 'radiogroup',
  UPLOAD = 'upload',
  TEXTAREA = 'textarea',
}

export interface IFieldData {
  name: string | number | (string | number)[];
  value?: any;
  touched?: boolean;
  validating?: boolean;
  errors?: string[];
  placeHolder?: string;
  nameInput?: NAME_INPUT;
  label?: string;
  rules?: { type?: string; required: boolean; message: string };
}

export interface IGeneralFormProps {
  onChange: (fields: IFieldData[]) => void;
  fields: IFieldData[];
  onChangeValue: (changeFields: IFieldData[], fields: IFieldData[]) => void;
}
