import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import { getFollowTagList } from '../../apis/api/tag';
import HashTag from '../../components/HashTag/HashTag';
import { addTokenToHeaders } from '../../apis/utils/authInstance';
import { useRecoilValue } from 'recoil';
import { memberTokenState, memberInfoState } from '../../states/user';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(1);
  const [hasFollowingHashTags, setHasFollowingHashTags] = useState(true);

  const jwtToken = useRecoilValue(memberTokenState);
  const user = useRecoilValue(memberInfoState);

  addTokenToHeaders(jwtToken);

  useEffect(() => {
    if (specificTag === -1) {
      // 팔로우 중인 해시태그 api 호출
      // (async () => {
      //   const data = await getFollowTagList(user.memberId);
      //   if (data.length === 0) setHasFollowingHashTags(false);
      //   else setHasFollowingHashTags(true);
      // })();
    }
  }, [specificTag]);

  return (
    <FeedContainer>
      <SearchDiv>
        <Search>
          <HashTag
            editable={true}
            hashTagLimit={1}
            setSpecificTag={setSpecificTag}
          />
        </Search>
      </SearchDiv>
      <Feed
        specificTag={specificTag}
        hasFollowingHashTags={hasFollowingHashTags}
        getFeedData={specificTag === -1 ? getFollowingFeed : getSpecificFeed}
      />
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
