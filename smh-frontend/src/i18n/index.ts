import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import translationRu from './locales/ru/translation.json';

export const defaultNS = 'translationRu';
export const resources = {
  ru: {
    translationRu,
  },
} as const;

i18n.use(initReactI18next).init({
  lng: 'ru',
  ns: 'translationRu',
  defaultNS,
  fallbackLng: 'ru',
  resources,
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;
