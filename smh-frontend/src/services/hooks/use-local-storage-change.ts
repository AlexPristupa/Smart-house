import { useEffect } from 'react';

/** Хук, слушающий изменения в локальном хранилище и по изменению возвращающий колбэк. */
export const useLocalStorageChange = (cb: (storage: Storage) => void) =>
  useEffect(() => {
    window.addEventListener('storage', () => {
      cb && cb(localStorage);
    });
  }, [cb]);
