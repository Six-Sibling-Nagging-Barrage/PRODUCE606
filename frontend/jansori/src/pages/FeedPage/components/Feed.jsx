import React, { useState } from 'react';
import InfiniteScroll from './InfiniteScroll';
import TodoPostList from './TodoPostList';
import tw, { styled } from 'twin.macro';

const Feed = (props) => {
  const { specificTag, getFeedData } = props;

  const [todoPostList, setTodoPostList] = useState([]);

  return (
    <FeedContainer>
      <TodoPostList todoPostList={todoPostList} />
      <InfiniteScroll
        specificTag={specificTag}
        setTodoPostList={setTodoPostList}
        getFeedData={getFeedData}
      />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  ${tw`w-full m-auto md:w-2/5`}
`;

export default Feed;
