import defaultInstance from '../utils/defaultInstance';

// 메인페이지이에서 랜덤으로 잔소리 호출하는 부분
export const getRandomNag = async () => {
  try {
    const { data } = await defaultInstance.get(`/nags/main-page`);
    return data;
  } catch (err) {
    console.log(err);
  }
};

// 잔소리함 페이지에서 ranking top 5 호출
export const getRankingNag = async () => {
  try {
    const { data } = await defaultInstance.get(`/nags/nag-rank`);
    return data;
  } catch (err) {
    console.log(err);
  }
};
