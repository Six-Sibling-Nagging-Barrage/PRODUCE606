import defaultInstance from '../utils/defaultInstance';
import { authInstance } from '../../apis/utils/authInstance';

// 회원가입
export const createSignUp = async (user) => {
  try {
    const { data } = await defaultInstance.post(`/auth/signup`, user, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};

// 로그인
export const createLogin = async (user) => {
  try {
    const { data } = await defaultInstance.post(`/auth/login`, user, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};

// 로그아웃
export const getLogout = async () => {
  try {
    const { data } = await authInstance.get(`/logout`);
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};

// 회원 정보 최초 입력
export const createProfile = async (profile) => {
  try {
    const { data } = await authInstance.post(`/register`, profile, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};

// 정보 수정
export const updateProfile = async (profile) => {
  try {
    const { data } = await authInstance.post(`/members/update`, profile, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};

// 멤버 프로필 조회
export const getMemberProfile = async (memberId) => {
  try {
    const { data } = await authInstance.get(`/members/${memberId}/profile`);
    return data;
  } catch (e) {
    console.log(e.response);
    return e.response;
  }
};
