import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import tw, { css, styled } from 'twin.macro';
import { useSetRecoilState } from 'recoil';
import { navBarState } from '../../../states/user';
import { todoTodoDetailModalState } from '../../../states/todo';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import SnackBar from '../../../components/UI/SnackBar';
import { useImageErrorHandler } from '../../../hooks/useImageErrorHandler';
import { altImageUrl } from '../../../constants/image';

const NagCommentItem = (props) => {
  const navigate = useNavigate();
  const setNavBar = useSetRecoilState(navBarState);
  const { isMemberNag, isMine, todoId, nag, toggleLike, toggleUnlock } = props;
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const setTodoDetailModal = useSetRecoilState(todoTodoDetailModalState);

  const handleImgError = useImageErrorHandler();

  const handleLikeClick = (unlocked) => {
    if (!unlocked) {
      setSnackBarMessage('잔소리 잠금해제를 해야 좋아요를 누를 수 있어요..');
      setShowSnackBar(true);
      return;
    }
    toggleLike({ todoId, nagId: nag.nagId });
  };

  const handleUnlockNag = async () => {
    toggleUnlock({ todoId, nagId: nag.nagId });
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  const handleProfileButton = () => {
    setNavBar(2);
    setTodoDetailModal(false);
    navigate(`/profile?id=${encodeURIComponent(nag.nagMember.memberId)}`);
  };

  return (
    <CommentContainer>
      <Profile>
        {isMemberNag ? (
          <ProfileImg
            onClick={handleProfileButton}
            isMemberNag={isMemberNag}
            src={nag.nagMember.imageUrl ? nag.nagMember.imageUrl : altImageUrl}
            onError={handleImgError}
          />
        ) : (
          <ProfileImg
            isMemberNag={isMemberNag}
            src={nag.nagMember.imageUrl ? nag.nagMember.imageUrl : altImageUrl}
            onError={handleImgError}
          />
        )}
        <NickName>{isMemberNag && nag.nagMember.nickname}</NickName>
      </Profile>
      <Bubble>
        <CommentContentWrapper>{nag.content}</CommentContentWrapper>
        {isMemberNag && (
          <ButtonGroup>
            {!nag.unlocked && (
              <UnlockButton onClick={() => handleUnlockNag(nag.nagId)}>
                <UnlockImg src={lockIcon} />
              </UnlockButton>
            )}
            <LikeButton onClick={() => handleLikeClick(nag.unlocked)}>
              {nag.isLiked ? (
                <LikeImg src={likeIcon} />
              ) : (
                <LikeImg
                  src={likeIcon}
                  filter="invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);"
                />
              )}
              <LikeCount>{nag.likeCount}</LikeCount>
            </LikeButton>
          </ButtonGroup>
        )}
      </Bubble>
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </CommentContainer>
  );
};

const CommentContainer = styled.div`
  display: flex;
  position: relative;
  & .profile-img {
    width: 50px;
  }
  margin: 10px 0;
`;

const Profile = styled.div`
  height: fit-content;
  width: 15%;
`;

const NickName = styled.div`
  margin-top: 5px;
  font-size: 12px;
`;

const ProfileImg = styled.img`
  ${(props) =>
    props.isMemberNag
      ? css`
          ${tw`w-10 h-10 rounded-full`}
        `
      : css`
          ${tw`w-14 h-14 rounded-full`}
        `}
  margin: 0 auto;
  object-fit: cover;
`;

const CommentContentWrapper = styled.div`
  position: relative;
  line-height: 18px;
  text-align: left;
  top: 50%;
  padding-right: 5px;
  font-size: 15px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%)
    contrast(83%);
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

const ButtonGroup = styled.div`
  display: flex;
  width: fit-content;
`;

const UnlockButton = styled.button`
  max-width: 23px;
  margin: 0 8px;
  display: flex;
  align-items: center; /* 수직 중앙 정렬 */
`;

const LikeButton = styled.button`
  width: 20px;
`;

const LikeImg = styled.img`
  filter: ${(props) => props.filter};
  padding-top: 5px;
`;

const LikeCount = styled.div`
  margin-top: 2px;
  font-size: 12px;
`;

const Bubble = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  min-height: 60px;
  height: fit-content;
  margin-left: 10px;
  width: 100%;
  background-color: rgb(244, 244, 244);
  border-radius: 20px;
  padding: 10px 20px;
  &:after {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    width: 0;
    height: 0;
    border: 8px solid transparent;
    border-right-color: rgb(244, 244, 244);
    border-left: 0;
    margin-top: -8px;
    margin-left: -8px;
  }
`;

export default NagCommentItem;
