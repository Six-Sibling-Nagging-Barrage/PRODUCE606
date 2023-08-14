import React, { useEffect, useState } from 'react';
import Feed from './components/Feed';
import tw, { styled } from 'twin.macro';
import { getFollowingFeed, getSpecificFeed } from '../../apis/api/todo';
import HashTag from '../../components/HashTag/HashTag';
import { memberIdState, memberRoleState } from '../../states/user';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { getFollowTagList, createFollowTag } from '../../apis/api/tag';
import { followingTagsState } from '../../states/tag';
import PersonaReaction from './components/PersonaReaction';
import SnackBar from '../../components/UI/SnackBar';
import Button from '../../components/UI/Button';
import FeedBackground from '../../components/UI/FeedBackground';

const FeedPage = () => {
  const [specificTag, setSpecificTag] = useState(-1);
  const [hashTagList, setHashTagList] = useState([]);
  const [currentPostId, setCurrentPostId] = useState(-1);
  const [personaReaction, setPersonaReaction] = useState([]);

  const memberId = useRecoilValue(memberIdState);
  const [followingTags, setFollowingTags] = useRecoilState(followingTagsState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const [isFollowing, setIsFollowing] = useState(false);

  useEffect(() => {
    // 팔로우 중인 해시태그 api 호출
    (async () => {
      const data = await getFollowTagList(memberId);
      setFollowingTags(data.tags);
    })();
  }, []);

  useEffect(() => {
    setIsFollowing(followingTags.some((item) => item.tagId === specificTag));
  }, [specificTag]);

  useEffect(() => {
    if (hashTagList.length === 0) return setSpecificTag(-1);
    setSpecificTag(hashTagList[0].tagId);
  }, [hashTagList]);

  const handleFollowHashTag = async () => {
    const data = await createFollowTag(specificTag);
    if (data.data.isFollowed) {
      setSnackBarMessage('태그를 구독했어요.');
      setIsFollowing(true);
      return setShowSnackBar(true);
    } else {
      setSnackBarMessage('태그 구독을 취소했어요.');
      setIsFollowing(false);
      return setShowSnackBar(true);
    }
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <FeedBody>
      <FeedBackground />
      <FeedContainer>
        <Left>
          <Search>
            {specificTag !== -1 && (
              <Follow>
                {isFollowing ? (
                  <Button cancel onClick={handleFollowHashTag}>
                    이 태그 구독 취소할래요...
                  </Button>
                ) : (
                  <Button normal onClick={handleFollowHashTag}>
                    이 태그 구독할래요!
                  </Button>
                )}
              </Follow>
            )}
            <SearchBar>
              <div>
                <HashTag
                  editable={true}
                  creatable={false}
                  hashTagLimit={1}
                  hashTagList={hashTagList}
                  setHashTagList={setHashTagList}
                  setSpecificTag={setSpecificTag}
                  absolute={true}
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
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </FeedBody>
  );
};

const FeedBody = styled.div`
  height: 100%;
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
  margin: 6px 15px;
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
