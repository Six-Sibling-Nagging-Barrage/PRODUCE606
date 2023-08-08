import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import profileImg from '../assets/profileImg.png';

// localStorage에 저장되며, key 이름은 'recoil-persist'
const { persistAtom } = recoilPersist();

export const isLoginState = atom({
  key: 'isLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const profileImgState = atom({
  key: 'profileImgState',
  default: profileImg,
  effects_UNSTABLE: [persistAtom],
});

export const memberNameState = atom({
  key: 'memberNameState',
  default: 'membername',
  effects_UNSTABLE: [persistAtom],
});

export const memberTokenState = atom({
  key: 'memberTokenState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const memberInfoState = atom({
  key: 'memberInfoState',
  default: {
    memberId: null,
    email: '',
    nickname: '',
    imageUrl: '',
    ticket: 0,
  },
  effects_UNSTABLE: [persistAtom],
});

export const memberIdState = atom({
  key: 'memberIdState',
  default: null,
  effects_UNSTABLE: [persistAtom],
});

export const memberRoleState = atom({
  key: 'memberRoleState',
  default: 'GUEST',
  effects_UNSTABLE: [persistAtom],
});
