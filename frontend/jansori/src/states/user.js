import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';
import profileImg from '../assets/profileImg.png';

// // localStorage에 저장되며, key 이름은 'recoil-persist'
const { persistAtom } = recoilPersist();

const localStorageEffect =
  (key) =>
  ({ setSelf, onSet }) => {
    const savedValue = localStorage.getItem(key);
    if (savedValue != null) {
      setSelf(JSON.parse(savedValue));
    }

    onSet((newValue, _, isReset) => {
      isReset
        ? localStorage.removeItem(key)
        : localStorage.setItem(key, JSON.stringify(newValue));
    });
  };

export const isLoginState = atom({
  key: 'isLoginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const profileImgState = atom({
  key: 'profileImgState',
  default:
    'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png',
  effects_UNSTABLE: [persistAtom],
});

export const memberNicknameState = atom({
  key: 'memberNicknameState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const memberTokenState = atom({
  key: 'memberTokenState',
  default: '',
  effects: [localStorageEffect('member_access_token')],
});

export const memberRefreshTokenState = atom({
  key: 'memberRefreshTokenState',
  default: '',
  effects: [localStorageEffect('member_refresh_token')],
});

export const memberTokenExpState = atom({
  key: 'memberTokenExpState',
  default: null,
  effects: [localStorageEffect('member_token_exp')],
});

export const memberInfoState = atom({
  key: 'memberInfoState',
  default: {
    memberId: null,
    email: '',
    nickname: '',
    imageUrl: '',
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

export const ticketState = atom({
  key: 'ticketState',
  default: 0,
  effects_UNSTABLE: [persistAtom],
});

export const navBarState = atom({
  key: 'navBarState',
  default: -1,
  effects_UNSTABLE: [persistAtom],
});
