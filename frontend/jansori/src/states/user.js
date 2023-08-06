import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

// localStorage에 저장되며, key 이름은 'recoil-persist'
const { persistAtom } = recoilPersist();

export const isLogin = atom({
  key: 'isLogin',
  default: false,
  effects_UNSTABLE: [persistAtom],
});
