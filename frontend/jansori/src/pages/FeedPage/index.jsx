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
      <SearchDiv>
        <Search>
          <HashTag hashTagLimit={1} setSpecificTag={setSpecificTag} />
        </Search>
      </SearchDiv>
      <Feed specificTag={specificTag} getFeedData={getFeedData} />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  display: flex;
  padding-top: 72px;
  justify-content: center;
`;

const SearchDiv = styled.div`
  position: relative;
`;

const Search = styled.div`
  width: fit-content;
  margin: 0 auto;
  position: absolute;
  top: 10px;
  right: 10px;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
`;

export default FeedPage;
