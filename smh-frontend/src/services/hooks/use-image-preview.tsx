import { useState } from 'react';
import { getBase64 } from 'services/utils';

export interface IImagePreview {
  isVisible: boolean;
  image: string;
  title: string;
}

export const useImagePreview = () => {
  const [preview, setPreview] = useState<IImagePreview>({ isVisible: false, image: '', title: '' });

  const handleCancel = () => setPreview({ ...preview, isVisible: false });

  const handlePreview = async file => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }

    setPreview({
      isVisible: true,
      image: file.url || file.preview,
      title: file.name || file.url.substring(file.url.lastIndexOf('/') + 1),
    });
  };

  return { preview, handleCancel, handlePreview };
};
