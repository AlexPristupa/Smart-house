import { useState } from 'react';

export const useChangeFormValues = () => {
  const [isValuesChange, setIsValuesChange] = useState(false);
  const onChangeFormValues = () => {
    setIsValuesChange(true);
  };

  return { isValuesChange, onChangeFormValues };
};
