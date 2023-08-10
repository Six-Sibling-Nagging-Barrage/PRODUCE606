import { atom } from 'recoil';

// 팔로우 중인 해시태그 리스트
export const followingTagsState = atom({
  key: 'followingTagsState',
  default: [],
});
