import axios from 'axios';

// 인증이 필요한 api를 위한 instance
// 헤더에 토큰 넣어주는 작업 추가 필요
const authInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  withCredentials: true,
});

export default authInstance;
