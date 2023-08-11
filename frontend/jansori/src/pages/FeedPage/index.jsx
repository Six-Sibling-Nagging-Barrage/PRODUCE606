import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import tw, { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import HashTag from '../../components/HashTag/HashTag';
import { memberIdState, memberRoleState } from '../../states/user';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { getFollowTagList, createFollowTag } from '../../apis/api/tag';
import { followingTagsState } from '../../states/tag';
import Button from '../../components/UI/Button';
import PersonaReaction from './components/PersonaReaction';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(-1);
  const [hashTagList, setHashTagList] = useState([]);
  const [currentPostId, setCurrentPostId] = useState(-1);
  const [personaReaction, setPersonaReaction] = useState([]);

  const memberId = useRecoilValue(memberIdState);
  const [followingTags, setFollowingTags] = useRecoilState(followingTagsState);

  useEffect(() => {
    // 팔로우 중인 해시태그 api 호출
    (async () => {
      const data = await getFollowTagList(memberId);
      setFollowingTags(data.tags);
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
    <FeedBody>
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
          setPersonaReaction={setPersonaReaction}
          setCurrentPostId={setCurrentPostId}
          currentPostId={currentPostId}
        />
        <Right>
          {currentPostId > -1 && (
            <PersonaReaction
              personaReaction={personaReaction}
              setPersonaReaction={setPersonaReaction}
              setCurrentPostId={setCurrentPostId}
              currentPostId={currentPostId}
            />
          )}
        </Right>
      </FeedContainer>
    </FeedBody>
  );
};

const FeedBody = styled.div`
  background-color: #f4f4fa;
`;

const FeedContainer = styled.div`
  display: flex;
  padding-top: 72px;
  justify-content: center;
`;

const Left = styled.div`
  position: relative;
  width: 32%;
`;

const Search = styled.div`
  margin: 0 auto;
  display: flex;
  position: absolute;
  top: 10px;
  right: 40px;
`;

const Follow = styled.div`
  margin: 0 20px;
`;

const SearchBar = styled.div`
  border-radius: 20px;
  width: fit-content;
  position: relative;
  background: #ffffff;
  border-radius: 0.4em;
  &:after {
    content: '';
    position: absolute;
    right: 0;
    top: 50%;
    width: 0;
    height: 0;
    border: 25px solid transparent;
    border-left-color: #ffffff;
    border-right: 0;
    margin-top: -25px;
    margin-right: -23px;
  }
`;

const Right = styled.div`
  position: relative;
  width: 32%;
`;

export default FeedPage;
