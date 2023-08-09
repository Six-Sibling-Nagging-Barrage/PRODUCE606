import axios, { AxiosError } from 'axios';
import { isExpired } from '../../utils/decodeJwt';

// 인증이 필요한 api를 위한 instance
export const authInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

const accessToken = localStorage.getItem('member_access_token');

if (accessToken) {
  authInstance.defaults.headers.common[
    'Authorization'
  ] = `Bearer ${accessToken.replace('"', '')}`;
}

authInstance.interceptors.request.use(
  async (request) => {
    const accessToken = localStorage.getItem('member_access_token');
    const refreshToken = localStorage.getItem('member_refresh_token');
    const exp = localStorage.getItem('member_token_exp');

    console.log(exp);
    console.log(isExpired(exp));

    if (accessToken && isExpired(exp)) {
      console.log(accessToken);
      console.log(exp);
      console.log('만료됨');
      const formData = new FormData();

      formData.append('accessToken', accessToken);
      formData.append('refreshToken', refreshToken.replace('"', ''));

      // FormData 내용 출력
      for (const pair of formData.entries()) {
        console.log(pair[0], pair[1]);
      }

      try {
        const { data } = await axios.post(`/auth/reissue`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });

        const newAccessToken = data.data.accessToken;
        const newRefreshToken = data.data.refreshToken;

        if (request.headers) {
          request.headers['Authorization'] = `Bearer ${newAccessToken}`;
          authInstance.defaults.headers.common[
            'Authorization'
          ] = `Bearer ${newAccessToken}`;
        }

        localStorage.setItem('member_access_token', newAccessToken);
        localStorage.setItem('member_refresh_token', newRefreshToken);
      } catch (e) {
        authInstance.defaults.headers.common = {};
        localStorage.removeItem('member_access_token');
        localStorage.removeItem('member_refresh_token');
        throw new AxiosError('토큰이 만료되었습니다.');
      }
    }
    return request;
  },
  (error) => {
    return Promise.reject(error);
  }
);
