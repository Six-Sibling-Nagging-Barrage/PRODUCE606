import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import HashTag from '../../components/HashTag/HashTag';
import { memberIdState, memberRoleState } from '../../states/user';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { getFollowTagList, createFollowTag } from '../../apis/api/tag';
import { followingTagsState } from '../../states/tag';
import Button from '../../components/UI/Button';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(-1);
  const [hashTagList, setHashTagList] = useState([]);

  const memberId = useRecoilValue(memberIdState);
  const [followingTags, setFollowingTags] = useRecoilState(followingTagsState);

  useEffect(() => {
    // 팔로우 중인 해시태그 api 호출
    (async () => {
      const data = await getFollowTagList(memberId);
      setFollowingTags(data.tags);
      console.log(data.tags);
    })();
  }, []);

  useEffect(() => {
    if (hashTagList.length === 0) return setSpecificTag(-1);
    setSpecificTag(hashTagList[0].tagId);
  }, [hashTagList]);

  const handleFollowHashTag = async () => {
    const data = await createFollowTag(specificTag);
    console.log(data);
  };

  const isFollowingTag = (tagId) => {
    console.log(followingTags);
    return followingTags.some((item) => item.tagId === tagId);
  };

  return (
    <FeedContainer>
      <Left>
        <Search>
          {specificTag !== -1 && (
            <Follow>
              <Button
                normal
                onClick={handleFollowHashTag}
                label={isFollowingTag(specificTag) ? '구독취소' : '구독하기'}
              ></Button>
            </Follow>
          )}
          <SearchBar>
            <div>
              <HashTag
                editable={true}
                hashTagLimit={1}
                hashTagList={hashTagList}
                setHashTagList={setHashTagList}
                setSpecificTag={setSpecificTag}
              />
            </div>
          </SearchBar>
        </Search>
      </Left>
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

const Left = styled.div`
  position: relative;
  width: 30%;
`;

const Search = styled.div`
  margin: 0 auto;
  display: flex;
  position: absolute;
  top: 10px;
  right: 10px;
`;

const Follow = styled.div`
  margin: 0 20px;
`;

const SearchBar = styled.div`
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
  width: fit-content;
`;

const Right = styled.div`
  width: 30%;
`;

export default FeedPage;
