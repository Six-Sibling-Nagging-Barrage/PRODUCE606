import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import { getNotificationCheck } from '../../apis/api/notification';
import NotificationItem from './NotificationItem';

const NotificationList = () => {
  const pageSize = 20;

  const [notifications, setNotifications] = useState([]);
  const [hasNext, setHasNext] = useState(true);
  const [hasNextCursor, setHasNextCursor] = useState(0);
  const [isLoading, setIsLoading] = useState(false);

  const fetchNotifications = async (cursor) => {
    setIsLoading(true);
    const { data } = await getNotificationCheck(cursor, pageSize);
    console.log(data);
    if (data.code === '200') {
      setNotifications([...notifications, ...data.data.notifications]);
      setHasNext(data.data.hasNext); //다음 무한 스크롤 값이 있는 경우 설정
      if (hasNext) setHasNextCursor(data.data.nextCursor);
    }
    setIsLoading(false);
  };

  const fetchNotificationsMore = () => {
    if (!isLoading && hasNext) {
      fetchNotifications(notifications[notifications.length - 1].notificationId);
    }
  };

  useEffect(() => {
    fetchNotifications(); 
  }, []);

  useEffect(() => {
    const checkScroll = () => {
      const scrollTop = document.documentElement.scrollTop;
      const windowHeight = window.innerHeight;
      const documentHeight = document.documentElement.scrollHeight;

      if (scrollTop + windowHeight >= documentHeight - 100) {
        fetchNotificationsMore();
      }
    };

    window.addEventListener('scroll', checkScroll);
    return () => window.removeEventListener('scroll', checkScroll);
  }, [notifications, isLoading, hasNext]);

  return (
    <NotificationListContainer>
      <BackgroundContainer />
      {/* 알람 리스트 호출하는 부분 */}
      <CenteredContainer>
        <NotificationListWrap>
          <ul>
            {notifications.map((notification) => (
              <li key={notification.notificationId}>{notification.content}</li>
            ))}
          </ul>
          {isLoading && <p>Loading...</p>}
          {!hasNext && <p>No more notifications.</p>}
        </NotificationListWrap>
      </CenteredContainer>
    </NotificationListContainer>
  );
};

export default NotificationList;

const NotificationListContainer = styled.div`
  ${tw`absolute right-1`}
  height: 80vh;
  width: 15vw;
  margin-top: 2vh;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;
const BackgroundContainer = styled.div`
  ${tw`
  z-40
  absolute
  w-full
  h-full`}
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  //   background-color: #f4f4fa;
  background-color: rgba(255, 255, 255, 0.7);
`;

const CenteredContainer = styled.div`
  ${tw`
  flex
  items-center
  justify-center
  h-full
  w-full`}
`;

const NotificationListWrap = styled.div`
  background-color: red;
  width: 95%;
  height: 95%;
  overflow-y: scroll;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;
