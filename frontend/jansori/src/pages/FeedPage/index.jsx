import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import HashTag from '../../components/HashTag/HashTag';
import { addTokenToHeaders } from '../../apis/utils/authInstance';
import { useRecoilValue } from 'recoil';
import { memberTokenState } from '../../states/user';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(-1);
  const [hashTagList, setHashTagList] = useState([]);

  const jwtToken = useRecoilValue(memberTokenState);

  addTokenToHeaders(jwtToken);

  useEffect(() => {
    if (hashTagList.length === 0) return setSpecificTag(-1);
    setSpecificTag(hashTagList[0].tagId);
  }, [hashTagList]);

  return (
    <FeedContainer>
      <SearchDiv>
        <Search>
          <div>
            <HashTag
              editable={true}
              hashTagLimit={1}
              hashTagList={hashTagList}
              setHashTagList={setHashTagList}
              setSpecificTag={setSpecificTag}
            />
          </div>
        </Search>
      </SearchDiv>
      <Feed
        specificTag={specificTag}
        getFeedData={specificTag === -1 ? getFollowingFeed : getSpecificFeed}
      />
      <Right></Right>
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
  width: 30%;
`;

const Search = styled.div`
  margin: 0 auto;
  position: absolute;
  top: 10px;
  right: 10px;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  width: fit-content;
`;

const Right = styled.div`
  width: 30%;
`;

export default FeedPage;
