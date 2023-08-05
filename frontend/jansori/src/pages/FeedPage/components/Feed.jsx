import React, { useState } from 'react';
import InfiniteScroll from './InfiniteScroll';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import TodoPost from './TodoPost';

const Feed = (props) => {
  const { specificTag, getFeedData } = props;

  const [todoPostList, setTodoPostList] = useState([]);
  const [currentPostId, setCurrentPostId] = useState(-1);
  const [personaReaction, setPersonaReaction] = useState([]);

  return (
    <FeedContainer>
      <ul>
        {todoPostList &&
          todoPostList.map((post, index) => {
            return (
              <TodoPost
                post={post}
                key={index}
                todoId={index} // 임시 데이터
                currentPostId={currentPostId}
                setCurrentPostId={setCurrentPostId}
                setPersonaReaction={setPersonaReaction}
              />
            );
          })}
      </ul>
      <InfiniteScroll
        specificTag={specificTag}
        setTodoPostList={setTodoPostList}
        getFeedData={getFeedData}
      />
      {currentPostId > -1 && (
        <PersonaReaction
          personaReaction={personaReaction}
          setCurrentPostId={setCurrentPostId}
        />
      )}
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  ${tw`w-2/5`}
  @media (min-width: 990px) and (max-width: 1200px) {
    width: 50%;
  }
  @media (min-width: 786px) and (max-width: 990px) {
    width: 60%;
  }
  @media (min-width: 680px) and (max-width: 786px) {
    width: 70%;
  }
  @media (max-width: 680px) {
    width: 90%;
  }
`;

export default Feed;
