import { useCallback, useState } from 'react';
import { TimePickerProps as IBaseTimePickerProps, TimePicker as BaseTimePicker } from 'antd';
import moment, { Moment } from 'moment';
import { Modify } from 'services';
import locale from 'antd/es/date-picker/locale/ru_RU';

export interface ITimePickerProps
  extends Modify<
    IBaseTimePickerProps,
    {
      value?: string;
      onChange?: (value: string, strictMode: string) => void;
    }
  > {}

export const TimePicker: React.FC<ITimePickerProps> = ({ value, format, onChange, ...restProps }) => {
  const defaultValue = moment('00:00', format?.toString());

  const [val, setVal] = useState<Moment | null>(value ? moment(value) : moment(defaultValue));

  const handleChange = useCallback(
    (newVal: Moment | null, timeString: string) => {
      console.log(timeString);
      const formatedValue = format ? moment(newVal).format(format as string) : moment(newVal).toISOString();

      setVal(newVal);
      onChange && onChange(formatedValue, timeString);
    },
    [format, onChange],
  );

  const handleSelect = (time: Moment) => {
    setVal(time);
  };

  const handleBlur = () => {
    const formatedValue = format ? moment(val).format(format as string) : moment(val).toISOString();
    onChange && onChange(formatedValue, formatedValue);
  };

  return (
    <BaseTimePicker
      {...restProps}
      onBlur={handleBlur}
      format={format}
      locale={locale}
      value={val}
      onChange={handleChange}
      onSelect={handleSelect}
    />
  );
};
