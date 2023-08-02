import React, { useEffect, useRef, useState } from 'react';
import profileImg from '../../../assets/profileImg.png';
import tw, { styled } from 'twin.macro';

const InfiniteScroll = (props) => {
  const { setTodoPostList } = props;

  const target = useRef();
  const [isLoading, setIsLoading] = useState(false);
  const [cursor, setCursor] = useState(null);
  const [hasMore, setHasMore] = useState(true);

  const pageSize = 10;

  const fetchMoreTodoPosts = async () => {
    if (!hasMore) return;
    setCursor((prev) => prev + pageSize);
  };

  const onIntersect = async ([entry], observer) => {
    if (entry.isIntersecting && !isLoading) {
      observer.unobserve(entry.target);
      await fetchMoreTodoPosts();
      observer.observe(entry.target);
    }
  };

  useEffect(() => {
    let observer;
    if (target.current) {
      observer = new IntersectionObserver(onIntersect, {
        threshold: 1,
      });
      observer.observe(target.current);
    }
    return () => observer && observer.disconnect();
  }, [onIntersect]);

  useEffect(() => {
    setIsLoading(true);
    try {
      // api 호출
      // /todo/feed/following?cursor={cursor}&size={pageSize}
      setTodoPostList((prev) => [
        ...prev,
        ...Array.from({ length: pageSize }, () => ({
          todoMember: {
            memberId: 1,
            nickname: 'User001',
            imageUrl: profileImg,
          },
          todo: {
            todoId: 107,
            display: true,
            finished: false,
            content: 'API 1개 완성',
            todoAt: '2023-08-01',
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
            personas: [
              {
                todoPersonaId: 61,
                personaId: 1,
                likeCount: 0,
              },
              {
                todoPersonaId: 62,
                personaId: 2,
                likeCount: 0,
              },
              {
                todoPersonaId: 63,
                personaId: 3,
                likeCount: 77,
              },
              {
                todoPersonaId: 64,
                personaId: 4,
                likeCount: 0,
              },
              {
                todoPersonaId: 65,
                personaId: 5,
                likeCount: 0,
              },
              {
                todoPersonaId: 66,
                personaId: 6,
                likeCount: 100,
              },
            ],
          },
          nag: {
            nagId: 5,
            unlocked: null,
            content: 'ㄴㅇ ㅈㄱㄱ ㅁㅁㅎㄷ ㄴㄱㄱㄴ ㅋㄷㅎㄷ',
            likeCount: 5,
            nagMember: {
              memberId: 2,
              nickname: 'User002',
              imageUrl: profileImg,
            },
          },
        })),
      ]); // dummy data
    } catch (e) {
      console.log(e);
    } finally {
      setIsLoading(false);
    }
  }, [cursor]);

  return <Target ref={target}>{isLoading && <div> 로딩중 ... </div>}</Target>;
};

const Target = styled.div`
  bottom: 0;
  left: 0;
  right: 0;
  margin-top: 10px;
  text-align: center;
  line-height: 28px;
`;

export default InfiniteScroll;
