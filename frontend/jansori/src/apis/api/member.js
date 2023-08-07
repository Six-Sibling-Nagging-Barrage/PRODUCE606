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
    console.log(e);
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
    console.log(e);
  }
};

// 로그아웃
export const getLogout = async () => {
  try {
    const { data } = await authInstance.get(`/logout`);
    return data;
  } catch (e) {
    console.log(e);
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
    console.log(e);
  }
};

// 정보 수정
export const updateProfile = async (profile) => {
  try {
    const { data } = await authInstance.put(`/members`, profile, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    return data;
  } catch (e) {
    console.log(e);
  }
};

// 나의 프로필 조회
export const getMyProfile = async () => {
  try {
    const { data } = await authInstance.get(`/members/my/profile`);
    return data.data;
  } catch (e) {
    console.log(e);
  }
};
