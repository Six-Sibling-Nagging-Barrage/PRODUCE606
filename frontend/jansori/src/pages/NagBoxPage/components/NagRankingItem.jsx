import React, { useState } from 'react';
import tw, { css, styled } from 'twin.macro';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../../states/user';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import { altImageUrl } from '../../../constants/image';
import SpeechBubble from '../../../components/UI/SpeechBubble';
import SnackBar from '../../../components/UI/SnackBar';

const NagRankingItem = (props) => {
  const { nag, isodd, toggleLike, toggleUnlock } = props;
  const memberId = useRecoilValue(memberIdState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const handleLikeClick = () => {
    if (!nag.unlocked) {
      setSnackBarMessage('잔소리 잠금해제를 해야 좋아요를 누를 수 있어요..');
      setShowSnackBar(true);
      return;
    }
    toggleLike({ nagId: nag.nagId });
  };

  const handleUnlockNag = async () => {
    toggleUnlock({ nagId: nag.nagId });
  };

  const handleImgError = (e) => {
    e.target.src = altImageUrl;
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <NagRankingItemContainer isodd={isodd}>
      <SpeechBubble isodd={isodd}>
        <SpeechBubbleWrap>
          <Profile>
            <ProfileImg src={nag.nagMember.imageUrl} onError={handleImgError} />
            <NickName>{nag.nagMember.nickname}</NickName>
          </Profile>
          <CommentContentWrapper>
            {memberId === nag.nagMember.memberId || nag.unlocked ? nag.content : nag.preview}
          </CommentContentWrapper>
          <ButtonGroup>
            {memberId === nag.nagMember.memberId ||
              (!nag.unlocked && (
                <ButtonItem onClick={() => handleUnlockNag(nag.nagId)}>
                  <UnlockImg src={lockIcon} />
                </ButtonItem>
              ))}
            <ButtonItem onClick={() => handleLikeClick(nag.unlocked)}>
              {nag.isLiked ? (
                <LikeImg src={likeIcon} />
              ) : (
                <LikeImg
                  src={likeIcon}
                  filter='invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);'
                />
              )}
              <LikeCount>{nag.likeCount}</LikeCount>
            </ButtonItem>
          </ButtonGroup>
        </SpeechBubbleWrap>
      </SpeechBubble>
      {showSnackBar && <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />}
    </NagRankingItemContainer>
  );
};

export default NagRankingItem;

const NagRankingItemContainer = styled.button`
  ${tw`
my-5
relative
w-4/5
`}
`;

const SpeechBubbleWrap = styled.div``;

const Profile = styled.div`
  display: flex;
  height: fit-content;
  width: fit-content;
  margin-left: 2vh;
`;
const ProfileImg = styled.img`
  ${(props) =>
    props.isMemberNag
      ? css`
          ${tw`w-7 rounded-full`}
        `
      : css`
          ${tw`w-7 rounded-full`}
        `}
  margin: 0 auto;
`;
const NickName = styled.div`
  height: fit-content;
  width: fit-content;
  margin-left: 10px;
  margin-top: 5px;
  font-size: 12px;
`;

const CommentContentWrapper = styled.div`
  position: relative;
  width: 70%;
  text-align: left;
  top: 50%;
  margin-top: 1vh;
  margin-left: 2vh;
  font-size: 20px;
  overflow-wrap: break-word;
  font-weight: semi-bold;
`;

const ButtonGroup = styled.div`
  position: absolute;
  display: flex;
  right: 15px;
  height: 64px;
  top: 50%;
  transform: translateY(-50%);
`;

const ButtonItem = styled.button`
  margin-right: 5px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%) contrast(83%);
  width: 40px;
  padding: 8px;
  &:hover {
    animation: shake 0.2s ease-in-out infinite;
  }

  @keyframes shake {
    0%,
    100% {
      transform: translateX(0);
    }
    25% {
      transform: translateX(-3px) rotate(-5deg);
    }
    75% {
      transform: translateX(3px) rotate(5deg);
    }
  }
`;

const LikeImg = styled.img`
  filter: ${(props) => props.filter};
  width: 20px;
  padding-top: 5px;
`;

const LikeCount = styled.div`
  margin-top: 2px;
  font-size: 12px;
`;
