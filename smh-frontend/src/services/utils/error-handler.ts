import i18n from 'i18n';

export const errorHandler = (error, form) => {
  error.forEach(error => {
    form.setFields([{ name: 'photo', errors: [' '] }]);
    form.setFields([{ name: error.fieldName, errors: [i18n.t<any>(`rules.${error.message}`)] }]);
  });
};
