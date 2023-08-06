import authInstance from '../utils/authInstance';

// Todo 작성
export const createTodo = async (todo) => {
  try {
    const { data } = await authInstance.post(`/todo`, todo, {
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
    const { data } = await authInstance.get(`/todo/my?date=${date}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

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
      `/todo/feed?tagId=${tagId}&cursor=${cursor}&size=${pageSize}`
    );
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 상세 조회
export const getTodoDetail = async (todoId) => {
  try {
    const { data } = await authInstance.get(`/todo/${todoId}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 나의 Todo 완료 토글
export const updateTodoComplete = async (todoId) => {
  try {
    const { data } = await authInstance.patch(`/todo/${todoId}`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 반응하기
export const createPersonaReaction = async ({ todoId, personaId }) => {
  try {
    const { data } = await authInstance.post(
      `/todo/${todoId}/react?personaId=${personaId}`
    );
    return data;
  } catch (e) {
    console.log(e);
  }
};

// Todo 캐릭터 잔소리 더보기
export const getPersonaNag = async (todoId) => {
  try {
    const { data } = await authInstance.get(`/todo/${todoId}/personas`);
    return data;
  } catch (e) {
    console.log(e);
  }
};
