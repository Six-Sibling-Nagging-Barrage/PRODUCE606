import { altImageUrl } from '../constants/image';

export function useImageErrorHandler() {
  const handleImgError = (e) => {
    e.target.src = altImageUrl;
  };

  return handleImgError;
}
