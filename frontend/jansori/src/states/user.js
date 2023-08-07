import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import profileImg from '../assets/profileImg.png';

// localStorage에 저장되며, key 이름은 'recoil-persist'
const { persistAtom } = recoilPersist();

export const isLogin = atom({
  key: 'isLogin',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const profileImgData = atom({
  key: 'image',
  default: profileImg,
  effects_UNSTABLE: [persistAtom],
});

export const memberNameData = atom({
  key: 'memberName',
  default: 'membername',
  effects_UNSTABLE: [persistAtom],
});
