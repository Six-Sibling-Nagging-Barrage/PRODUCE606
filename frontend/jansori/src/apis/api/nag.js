import authInstance from '../utils/authInstance';

// 내가 보낸 잔소리 목록 조회
export const getMyNagList = async () => {
  try {
    const { data } = await authInstance.get(`/nags/my-list`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 메인페이지용 잔소리 가져오기
export const getMainNags = async () => {
  try {
    const { data } = await authInstance.get(`/nags/main-page`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 잔소리 작성
export const createNag = async (nag) => {
  try {
    const { data } = await authInstance.post(`/nags`, nag, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 잔소리 좋아요
export const updateLikeNag = async (nagId) => {
  try {
    const { data } = await authInstance.post(`/nags/${nagId}/like`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 잔소리함 랭킹 조회
export const getNagRanking = async () => {
  try {
    const { data } = await authInstance.get(`/nags/nag-rank`);
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 잔소리 초성 해제
export const updateNagLock = async (nagId) => {
  try {
    const { data } = await authInstance.put(`/nags/${nagId}/unlock`);
    return data;
  } catch (e) {
    console.log(e);
  }
};