import { useState } from 'react';
import { Checkbox, Form } from 'antd';

export const CheckboxItem = ({ key, item }) => {
  const [isChecked, setIsChecked] = useState(item.value);

  const onChange = () => {
    setIsChecked(!isChecked);
  };

  console.log(item);

  return (
    <Form.Item key={key} name={item.name} label={item.label}>
      <Checkbox checked={isChecked} onChange={onChange}>
        {isChecked ? 'Выполнено' : 'В ожидании'}
      </Checkbox>
    </Form.Item>
  );
};
