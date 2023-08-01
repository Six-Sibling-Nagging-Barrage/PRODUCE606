import React from 'react';
import SearchBar from './components/SearchBar';
import Feed from './components/Feed';
import { styled } from 'twin.macro';

const FeedPage = () => {
  return (
    <FeedContainer>
      <SearchBar />
      <Feed />
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  padding-top: 72px;
`;

export default FeedPage;
