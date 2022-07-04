import { useCallback, useState } from 'react';
import { DatePickerProps as IBaseDatePickerprops, DatePicker as BaseDatePicker } from 'antd';
import moment, { Moment } from 'moment';
import { Modify } from 'services';
import locale from 'antd/es/date-picker/locale/ru_RU';

export interface IDatePickerProps
  extends Modify<
    IBaseDatePickerprops,
    {
      value?: string;
      onChange?: (value: string, strictMode: string) => void;
    }
  > {}

export const DatePicker: React.FC<IDatePickerProps> = ({ value, format, onChange, ...restProps }) => {
  const [val, setVal] = useState<Moment | null>(value ? moment(value) : null);

  const handleChange = useCallback(
    (newVal: Moment | null, dateString: string) => {
      const formatedValue = format ? moment(newVal).format(format as string) : moment(newVal).toISOString();

      setVal(newVal);
      onChange && onChange(formatedValue, dateString);
    },
    [format, onChange],
  );

  return <BaseDatePicker {...restProps} locale={locale} value={val} onChange={handleChange} />;
};
