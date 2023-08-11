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
    return e.response;
  }
};

// 닉네임 중복 검사
export const getAvailableNickname = async (nickname) => {
  try {
    const { data } = await defaultInstance.get(
      `/members/nickname?nickname=${nickname}`
    );
    return data;
  } catch (e) {
    console.log(e);
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
    console.log(e);
    return e.response;
  }
};

// 로그아웃
export const createLogout = async (user) => {
  try {
    const { data } = await authInstance.post(`/auth/logout`, user, {
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
        'Content-type': 'multipart/form-data',
        Accept: '*/*',
      },
    });
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};

// 멤버 프로필 조회
export const getMemberProfile = async (memberId) => {
  try {
    const { data } = await authInstance.get(`/members/${memberId}/profile`);
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};
