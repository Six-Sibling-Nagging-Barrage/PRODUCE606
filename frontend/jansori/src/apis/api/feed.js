import { defaultInstance, authInstance } from '../utils';

// 팔로우한 태그의 피드 조회
export const getFollowingFeed = async ({ cursor, pageSize }) => {
  try {
    const { data } = await authInstance.get(
      `/todo/feed/following?cursor=${cursor}&size=${pageSize}`
    );
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 특정 태그 피드 조회
export const getSpecificFeed = async ({ tagId, cursor, pageSize }) => {
  try {
    const { data } = await authInstance.get(
      `/todo/feed?tagId={tagId}&cursor=${cursor}&size=${pageSize}`
    );
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 상세 조회
export const getTodoDetail = async (todoId) => {
  try {
    const { data } = await defaultInstance.get(`/todo/${todoId}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 반응하기
export const createPersonaReaction = async ({ todoId, todoPersonaId }) => {
  try {
    const { data } = await authInstance.post(
      `/todo/${todoId}/${todoPersonaId}`
    );
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 잔소리 더보기
export const getPersonaNag = async (todoId) => {
  try {
    const { data } = await defaultInstance.get(`/todo/${todoId}/personas`);
    return data;
  } catch (e) {
    console.log(e);
  }
};
