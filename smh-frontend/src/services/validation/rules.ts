import { FormItemProps } from 'antd';
import i18n from 'i18n';
import { PluralMessageSelector } from 'i18n/utils/message-utils';

const messageSelector = new PluralMessageSelector('символ', 'символа', 'символов');
export const baseRules = {
  required: { required: true, message: i18n.t('rules.required') },
  max32: { max: 32, message: `${i18n.t('rules.max')} 32 ${messageSelector.getMessage(32)}` },
  min8: { min: 8, message: `${i18n.t('rules.min')} 8 ${messageSelector.getMessage(8)}` },
  max255: { max: 255, message: `${i18n.t('rules.max')} 255 ${messageSelector.getMessage(255)}` },
};

export const dateRules = {
  required: { type: 'object' as const, required: true, message: i18n.t('rules.required') /*MESSAGE_SYSTEM.MESSAGE_EROR_VALIDATING*/ },
};

export const isEqualToField =
  <T extends Object>(fieldName: Extract<keyof T, string>): Rule =>
  ({ getFieldValue }) => ({
    validator(_, value) {
      if (!value || getFieldValue(fieldName) === value) {
        return Promise.resolve();
      }
      return Promise.reject(new Error(i18n.t('rules.isEqualToField')));
    },
  });

type ArrayElement<A> = A extends readonly (infer T)[] ? T : never;

type Rule = ArrayElement<FormItemProps['rules']>;
