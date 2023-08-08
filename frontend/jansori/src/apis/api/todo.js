import { authInstance } from '../../apis/utils/authInstance';

// Todo 작성
export const createTodo = async (todo) => {
  try {
    const { data } = await authInstance.post(`/todos`, todo, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 나의 날짜별 Todo List 가져오기
export const getTodoListByDate = async (date) => {
  try {
    const { data } = await authInstance.get(`/todos/my?date=${date}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 멤버의 월별 투두 존재 여부 확인
export const getTodoListByDateByMember = async ({ memberId, date }) => {
  try {
    const { data } = await authInstance.get(`/todos/members/${memberId}/monthly?yearMonth=${date}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 팔로우한 태그의 피드 조회
export const getFollowingFeed = async ({ cursor, pageSize }) => {
  try {
    const url = cursor
      ? `/todos/feed/following?cursor=${cursor}&size=${pageSize}`
      : `/todos/feed/following?size=${pageSize}`;
    const { data } = await authInstance.get(url);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// 특정 태그 피드 조회
export const getSpecificFeed = async ({ cursor, tagId, pageSize }) => {
  try {
    const url = cursor
      ? `/todos/feed?cursor=${cursor}&tagId=${tagId}&size=${pageSize}`
      : `/todos/feed?tagId=${tagId}&size=${pageSize}`;
    const { data } = await authInstance.get(url);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 상세 조회
export const getTodoDetail = async (todoId) => {
  try {
    const { data } = await authInstance.get(`/todos/${todoId}`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// 나의 Todo 완료 토글
export const updateTodoComplete = async (todoId) => {
  try {
    const { data } = await authInstance.patch(`/todos/${todoId}`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 반응하기
export const createPersonaReaction = async ({ todoId, todoPersonaId }) => {
  try {
    const { data } = await authInstance.post(`/todos/${todoId}/${todoPersonaId}`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 잔소리 더보기
export const getPersonaNag = async (todoId) => {
  try {
    const { data } = await authInstance.get(`/todos/${todoId}/personas`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};
