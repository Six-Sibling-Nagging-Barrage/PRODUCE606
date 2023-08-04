import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import HashTag from '../../components/HashTag/HashTag';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(-1);
  const [getFeedData, setGetFeedData] = useState(null);

  useEffect(() => {
    if (specificTag === -1) {
      return setGetFeedData(() => getFollowingFeed);
    }
    setGetFeedData(() => getSpecificFeed);
  }, [specificTag]);

  return (
    <FeedContainer>
      <Search>
        <HashTag hashTagLimit={1} setSpecificTag={setSpecificTag} />
      </Search>
      <Feed specificTag={specificTag} getFeedData={getFeedData} />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  padding-top: 72px;
`;

const Search = styled.div`
  width: fit-content;
  margin: 0 auto;
`;

export default FeedPage;
