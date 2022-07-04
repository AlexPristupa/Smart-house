import { resources, defaultNS } from './';

declare module 'react-i18next' {
  interface CustomTypeOptions {
    defaultNS: typeof defaultNS;
    resources: typeof resources['ru'];
  }
}
