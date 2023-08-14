import { useSetRecoilState } from 'recoil';
import { profileImgState } from '../states/user';
import { memberInfoState } from '../states/user';
import { altImageUrl } from '../constants/image';

export function useImageErrorHandler() {
  const setProfileImg = useSetRecoilState(profileImgState);
  const setMemberInfo = useSetRecoilState(memberInfoState);

  const handleImgError = (e) => {
    e.target.src = altImageUrl;
    setMemberInfo((prev) => ({
      ...prev,
      imageUrl: altImageUrl,
    }));
    setProfileImg(altImageUrl);
  };

  return handleImgError;
}
