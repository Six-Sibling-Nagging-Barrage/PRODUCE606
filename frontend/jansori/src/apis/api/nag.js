import { authInstance } from '../../apis/utils/authInstance';
import defaultInstance from '../utils/defaultInstance';

// 내가 보낸 잔소리 목록 조회
export const getMyNagList = async ({ cursor, pageSize }) => {
  try {
    const url = cursor
      ? `/nags/my-list?cursor=${cursor}&size=${pageSize}`
      : `/nags/my-list?&size=${pageSize}`;
    const { data } = await authInstance.get(url);
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 멤버의 잔소리 목록 조회
export const getMemberNagList = async ({ memberId, cursor, pageSize }) => {
  try {
    const url = cursor
      ? `/nags/members?cursor=${cursor}&memberId=${memberId}&size=${pageSize}`
      : `/nags/members?memberId=${memberId}&size=${pageSize}`;
    const { data } = await authInstance.get(url);
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 메인페이지용 잔소리 가져오기
export const getMainNags = async () => {
  try {
    const { data } = await defaultInstance.get(`/nags/main-page`);
    return data.data;
  } catch (e) {
    console.log(e);
    return e.response;
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
    return e.response;
  }
};

// 잔소리 좋아요
export const updateLikeNag = async (nagId) => {
  try {
    const { data } = await authInstance.post(`/nags/${nagId}/like`);
    return data.data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 잔소리함 랭킹 조회
export const getNagRanking = async () => {
  try {
    const { data } = await authInstance.get(`/nags/nag-rank`);
    return data.data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 잔소리함 통계 조회
export const getNagBoxStatistics = async () => {
  try {
    const { data } = await authInstance.get(`/nags/nag-box/statistics`);
    return data.data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 잔소리 초성 해제
export const updateNagUnlock = async (nagId) => {
  try {
    const { data } = await authInstance.put(`/nags/${nagId}/unlock`);
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};
