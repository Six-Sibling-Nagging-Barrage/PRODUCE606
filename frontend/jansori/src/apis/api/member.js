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
