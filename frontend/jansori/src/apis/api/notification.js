import { authInstance } from '../../apis/utils/authInstance';

// 사용자 알림함 조회
export const getNotificationCheck = async ({ cursor, pageSize }) => {
  try {
    //null설정)
    const url = cursor
      ? `/notifications?cursor=${cursor}&size=${pageSize}`
      : `/notifications?&size=${pageSize}`;
    const { data } = await authInstance.patch(url);
    return data;
  } catch (e) {
    console.log(e);
    return e.resonse;
  }
};
