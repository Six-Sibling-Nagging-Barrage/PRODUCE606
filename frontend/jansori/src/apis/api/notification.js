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
    return e.response;
  }
};

//주기적으로 알람이 있는지 없는지
export const getNotificationUnRead = async () => {
  try {
    const { data } = await authInstance.get(`/notifications/unread`);
    return data;
  } catch (e) {
    console.log(e);
    return e.response;
  }
};
