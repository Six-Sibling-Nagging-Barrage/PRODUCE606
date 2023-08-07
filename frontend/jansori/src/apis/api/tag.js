import { authInstance } from '../../apis/utils/authInstance';

// 새로운 해시태그 생성 및 팔로우
export const createTag = async (searchText) => {
  try {
    const { data } = await authInstance.post(
      `/tags/create?keyword=${searchText}`
    );
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// 해시태그 자동완성 검색
export const getTagsAutoComplete = async (searchText) => {
  try {
    const { data } = await authInstance.get(
      `/tags/auto-complete?keyword=${searchText}`
    );
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// 해시태그 팔로우
export const createFollowTag = async (tagId) => {
  try {
    const { data } = await authInstance.post(`/tags/${tagId}/follow`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// 유저가 팔로우한 태그 목록 조회
export const getFollowTagList = async (memberId) => {
  try {
    const { data } = await authInstance.get(`/tags/${memberId}/list`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};
