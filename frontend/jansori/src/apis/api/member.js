import defaultInstance from '../utils/defaultInstance';
import authInstance from '../utils/authInstance';

// 회원가입/로그인 요청
export const createMember = async () => {
  try {
    const res = await defaultInstance.get(`/oauth2/authorization/google`);
    return res;
  } catch (e) {
    console.log(e);
  }
};

// 로그아웃
export const getLogout = async () => {
  try {
    const res = await authInstance.get(`/logout`);
    return res;
  } catch (e) {
    console.log(e);
  }
};

// 회원 정보 최초 입력
export const createProfile = async (profile) => {
  try {
    const res = await authInstance.post(`/register`, profile, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return res;
  } catch (e) {
    console.log(e);
  }
};

// 정보 수정
export const updateProfile = async (profile) => {
  try {
    const res = await authInstance.put(`/members`, profile, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return res;
  } catch (e) {
    console.log(e);
  }
};
