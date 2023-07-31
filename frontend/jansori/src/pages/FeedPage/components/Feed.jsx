import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import TodoPostList from './TodoPostList';
import MoreFetchTarget from './MoreFetchTarget';
import profileImg from '../../../assets/profileImg.png';

const Feed = () => {
  const [cursor, setCursor] = useState(0);
  const [postList, setPostList] = useState([]);

  useEffect(() => {
    setPostList((prev) => [
      ...prev,
      ...Array.from({ length: 10 }, () => ({
        id: 1,
        writer: {
          nickname: '팜하니',
          img: profileImg,
        },
        finished: false,
        date: '2023.07.26',
        content: '페이지 언능 만들기 ! ! !',
      })),
    ]); // dummy data
  }, [cursor]);

  return (
    <FeedContainer>
      <TodoPostList postList={postList} />
      <MoreFetchTarget items={postList} setCursor={setCursor} />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  ${tw`w-full m-auto md:w-2/5`}
`;

export default Feed;
