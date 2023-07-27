import React, { useEffect, useState } from 'react';
import tw from 'twin.macro';
import TodoPostList from './TodoPostList';
import MoreFetchTarget from './MoreFetchTarget';

const Feed = () => {
  const [cursor, setCursor] = useState(0);
  const [postList, setPostList] = useState([]);

  useEffect(() => {
    setPostList((prev) => [...prev, ...Array.from({ length: 10 }, () => 0)]); // dummy data
  }, [cursor]);

  return (
    <FeedContainer>
      <TodoPostList postList={postList} />
      <MoreFetchTarget items={postList} setCursor={setCursor} />
    </FeedContainer>
  );
};

const FeedContainer = tw.div`
  p-6 w-2/5 m-auto
`;

export default Feed;
