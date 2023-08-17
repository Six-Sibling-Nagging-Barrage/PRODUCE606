import React, { useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import { getNotificationCheck } from '../../apis/api/notification';
import NotificationItem from './NotificationItem';
import { useInfiniteQuery } from '@tanstack/react-query';
import InfiniteScroll from '../../pages/FeedPage/components/InfiniteScroll';

const NotificationList = () => {
  const pageSize = 20;

  let param = { cursor: null, pageSize };

  const { data, fetchNextPage, hasNextPage, isFetchingNextPage, isError, isLoading, refetch } =
    useInfiniteQuery(
      ['notifications'],
      ({ pageParam = param }) => fetchMoreNotifications(pageParam),
      {
        getNextPageParam: (lastPage) => {
          if (!lastPage?.hasNext) return undefined;
          return { cursor: lastPage.nextCursor, pageSize };
        },
      }
    );

  useEffect(() => {
    param = { cursor: null, pageSize };
    refetch();
  }, [refetch]);

  const fetchMoreNotifications = async (pageParam) => {
    const { data } = await getNotificationCheck(pageParam);
    return data;
  };

  return (
    <NotificationListContainer>
      <BackgroundContainer />
      {/* 알람 리스트 호출하는 부분 */}
      <CenteredContainer>
        <NotificationListWrap>
          {isLoading && <div>로딩 중 ....</div>}
          {isError && <div>알림을 불러오는데 실패했습니다.</div>}
          {data?.pages?.some((page) => page?.notifications?.length > 0) ? (
            <ul>
              {data?.pages?.map((page) =>
                page?.notifications?.map((notification) => (
                  <NotificationItem
                    key={notification.notificationId}
                    lastReadAt={page.lastReadAt}
                    createdAt={notification.createdAt}
                    content={notification.content}
                  />
                ))
              )}
            </ul>
          ) : (
            <NotYetAlarm>아직 알림이 없어요!</NotYetAlarm>
          )}
          <InfiniteScroll
            fetchNextPage={fetchNextPage}
            hasNextPage={hasNextPage}
            isFetchingNextPage={isFetchingNextPage}
          />
        </NotificationListWrap>
      </CenteredContainer>
    </NotificationListContainer>
  );
};

export default NotificationList;

const Background = styled.div`
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 30;
`;

const NotificationListContainer = styled.div`
  ${tw`absolute right-1`}
  z-index: 30;
  height: 60vh;
  width: 20rem;
  margin-top: 2vh;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;
const BackgroundContainer = styled.div`
  ${tw`
  absolute
  w-full
  h-full`}
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  // background-color: rgba(240, 240, 240, 0.7);
  background-color: white;
  z-index: 90;
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
  z-index: 100;
  width: 95%;
  max-height: 100%;
  padding: 10px 0;
  overflow-y: scroll;
  border-radius: 10px;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

const NotYetAlarm = styled.div`
  line-height: 60vh;
`;
