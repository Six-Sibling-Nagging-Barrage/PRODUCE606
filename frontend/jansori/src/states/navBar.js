import { atom } from 'recoil';

export const isHamburgerOpenState = atom({
  key: 'isHamburgerOpenState',
  default: false,
});

export const isProfileModalOpenState = atom({
  key: 'isProfileModalOpenState',
  default: false,
});

export const isNotificationModalOpenState = atom({
  key: 'isNotificationModalOpenState',
  default: false,
});
