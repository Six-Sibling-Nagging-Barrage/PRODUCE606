import axios, { AxiosError } from 'axios';
import { isExpired } from '../../utils/decodeJwt';

// 인증이 필요한 api를 위한 instance
export const authInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

authInstance.interceptors.request.use(
  async (request) => {
    const accessToken = localStorage
      .getItem('member_access_token')
      .replaceAll('"', '');
    const refreshToken = localStorage
      .getItem('member_refresh_token')
      .replaceAll('"', '');
    const exp = parseInt(localStorage.getItem('member_token_exp'));

    if (!accessToken) return;

    if (isExpired(exp)) {
      console.log(new Date(exp));
      console.log('만료됨');

      try {
        const { data } = await axios.post(
          `${process.env.REACT_APP_API_URL}/auth/reissue`,
          {
            accessToken,
            refreshToken,
          },
          {
            headers: {
              Authorization: `Bearer ${accessToken}`,
              'Content-Type': 'application/json',
            },
          }
        );

        console.log(data);
        const newAccessToken = data.data.accessToken;
        const newRefreshToken = data.data.refreshToken;
        const newExp = data.data.accessTokenExpiresIn;

        if (request.headers) {
          request.headers['Authorization'] = `Bearer ${newAccessToken}`;
          authInstance.defaults.headers.common[
            'Authorization'
          ] = `Bearer ${newAccessToken}`;
        }

        localStorage.setItem('member_access_token', newAccessToken);
        localStorage.setItem('member_refresh_token', newRefreshToken);
        localStorage.setItem('member_token_exp', newExp);
      } catch (e) {
        authInstance.defaults.headers.common = {};
        throw new AxiosError('토큰이 만료되었습니다.');
      }
    } else {
      request.headers['Authorization'] = `Bearer ${accessToken}`;
      authInstance.defaults.headers.common[
        'Authorization'
      ] = `Bearer ${accessToken}`;
    }
    return request;
  },
  (error) => {
    return Promise.reject(error);
  }
);
