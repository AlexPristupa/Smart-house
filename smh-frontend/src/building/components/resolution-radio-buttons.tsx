import React, { useState } from 'react';
import { Form, Radio } from 'antd';
import { useTranslation } from 'react-i18next';

export const ResolutionRadioButtons = ({ label, name }) => {
  const { t } = useTranslation();
  const [resolutionValue, setResolutionValue] = useState(t('button.resolutionBtn.unresolved'));

  const resolutionHandler = e => {
    setResolutionValue(e.target.value);
  };
  return (
    <Form.Item label={label} name={name}>
      <Radio.Group onChange={resolutionHandler} value={resolutionValue} defaultValue={t('button.resolutionBtn.unresolved')}>
        <Radio value={t('button.resolutionBtn.resolved')}>{t('service.form.title.resolved')}</Radio>
        <Radio value={t('button.resolutionBtn.unresolved')}>{t('service.form.title.unresolved')}</Radio>
      </Radio.Group>
    </Form.Item>
  );
};
