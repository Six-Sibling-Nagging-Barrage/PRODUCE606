import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import TodoPostList from './TodoPostList';
import MoreFetchTarget from './MoreFetchTarget';
import profileImg from '../../../assets/profileImg.png';

const Feed = () => {
  const [cursor, setCursor] = useState(null);
  const [postList, setPostList] = useState([]);
  const [hasMore, setHasMore] = useState(true);

  const pageSize = 10;

  useEffect(() => {
    // MoreFetchTarget에서 cursor가 변경되면 피드를 추가로 불러온다.
    // /todo/feed/following?cursor={cursor}&size={pageSize}
    if (hasMore) {
      setPostList((prev) => [
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
    }
  }, [cursor]);

  return (
    <FeedContainer>
      <TodoPostList postList={postList} />
      <MoreFetchTarget
        items={postList}
        setCursor={setCursor}
        pageSize={pageSize}
      />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  ${tw`w-full m-auto md:w-2/5`}
`;

export default Feed;
