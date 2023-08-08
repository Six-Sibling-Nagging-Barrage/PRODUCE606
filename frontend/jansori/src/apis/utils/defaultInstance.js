import axios from 'axios';

const defaultInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
});

export default defaultInstance;
