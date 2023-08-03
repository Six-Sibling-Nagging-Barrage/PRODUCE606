import React, { useEffect, useRef, useState } from 'react';
import profileImg from '../../../assets/profileImg.png';
import tw, { styled } from 'twin.macro';

const dummyData = {
  feed: [
    {
      todoId: 108,
      finished: false,
      content: '테스트 코드 작성',
      todoAt: '2023-08-02',
      tags: [
        {
          tagId: 1,
          tagName: '개발',
        },
        {
          tagId: 2,
          tagName: '코딩',
        },
      ],
      member: {
        memberId: 1,
        nickname: 'User001',
        imageUrl: profileImg,
      },
      nag: {
        nagId: 5,
        unlocked: false,
        isLiked: false,
        content: 'ㄴㅇ ㅈㄱㄱ ㅁㅁㅎㄷ ㄴㄱㄱㄴ ㅋㄷㅎㄷ',
        likeCount: 5,
        nagMember: {
          memberId: 2,
          nickname: 'User002',
          imageUrl: profileImg,
        },
      },
    },
  ],
  hasNext: true,
  nextCursor: 106,
};

const InfiniteScroll = (props) => {
  const { specificTag, setTodoPostList, getFeedData } = props;

  const target = useRef();
  const [isLoading, setIsLoading] = useState(false);
  const [cursor, setCursor] = useState(null);
  const [hasMore, setHasMore] = useState(true);

  const pageSize = 10;

  let nextCursor;

  const triggerMoreTodoPosts = () => {
    if (!hasMore) return;
    // setCursor(nextCursor); // 실제
    setCursor((prev) => prev + 10); // 임시
  };

  const onIntersect = ([entry], observer) => {
    if (entry.isIntersecting && !isLoading) {
      observer.unobserve(entry.target);
      triggerMoreTodoPosts();
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer;
    if (target.current) {
      console.log('타겟 존재');
      observer = new IntersectionObserver(onIntersect);
      observer.observe(target.current);
    }
    return () => observer && observer.disconnect();
  }, [onIntersect]);

  useEffect(() => {
    console.log('cursor');
    setIsLoading(true);
    // api 호출
    // let param;
    // if (specificTag === -1) {
    //   param = { cursor, pageSize };
    // } else {
    //   param = { specificTag, cursor, pageSize };
    // }
    // const data = await getFeedData(param);
    // setTodoPostList((prev) => [...prev, ...data.feed]);

    // dummy data
    const data = dummyData;
    setTodoPostList((prev) => [
      ...prev,
      ...Array.from({ length: 10 }, () => data.feed[0]),
    ]);
    setHasMore(data.hasNext);
    nextCursor = data.nextCursor;
    setIsLoading(false);
  }, [cursor]);

  return <Target ref={target}>{isLoading && <div> 로딩 중 ... </div>}</Target>;
};

const Target = styled.div`
  bottom: 0;
  height: 20px;
`;

export default InfiniteScroll;
