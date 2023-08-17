import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import tw, { styled } from 'twin.macro';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../../states/user';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import SnackBar from '../../../components/UI/SnackBar';
import { useImageErrorHandler } from '../../../hooks/useImageErrorHandler';
import { altImageUrl } from '../../../constants/image';
import AlertModal from '../../../components/UI/AlertModal';

const NagRankingItem = (props) => {
  const { nag, toggleLike, toggleUnlock, rank } = props;
  const memberId = useRecoilValue(memberIdState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleImgError = useImageErrorHandler();

  const handleLikeClick = () => {
    if (memberId !== nag.nagMember.memberId && !nag.unlocked) {
      setSnackBarMessage('잔소리 잠금을 해제하면 좋아요를 누를 수 있어요');
      setShowSnackBar(true);
      return;
    }
    toggleLike({ nagId: nag.nagId });
  };

  const handleClickUnlock = async () => {
    setIsModalOpen(true);
    document.body.style.overflow = 'hidden'; // 스크롤 막기
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    document.body.style.overflow = 'visible'; // 스크롤 활성화
  };

  const handleUnlockNag = async () => {
    toggleUnlock({ nagId: nag.nagId });
    handleCloseModal();
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <>
      <RankingItem>
        <RankingBadge rank={rank}>{rank}</RankingBadge>
        <Nag>
          <Profile>
            <Link
              to={`/profile?id=${encodeURIComponent(nag.nagMember.memberId)}`}
            >
              <ProfileImg
                src={
                  nag.nagMember.imageUrl ? nag.nagMember.imageUrl : altImageUrl
                }
                onError={handleImgError}
              />
              <NickName>{nag.nagMember.nickname}</NickName>
            </Link>
          </Profile>
          <NagContent>
            {nag.unlocked
              ? nag.content
              : nag.preview}
          </NagContent>
        </Nag>
        <ButtonBadge>
          {nag.unlocked ? (
            <LikeButtonItem onClick={handleLikeClick}>
              {nag.isLiked ? (
                <LikeImg src={likeIcon} />
              ) : (
                <LikeImg
                  src={likeIcon}
                  filter="invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);"
                />
              )}
              <LikeCount>{nag.likeCount}</LikeCount>
            </LikeButtonItem>
          ) : (
            <UnlockButtonItem onClick={handleClickUnlock}>
              <UnlockImg src={lockIcon} />
            </UnlockButtonItem>
          )}
        </ButtonBadge>
      </RankingItem>
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
      {isModalOpen && (
        <AlertModal
          setIsModalOpen={setIsModalOpen}
          handleAccept={handleUnlockNag}
          handleCancel={handleCloseModal}
          nag={nag}
        />
      )}
    </>
  );
};

export default NagRankingItem;

const Profile = styled.div`
  margin-bottom: 10px;
`;

const ProfileImg = styled.img`
  ${tw`w-8 h-8 rounded-full`}
  object-fit: cover;
  display: inline-block;
`;

const NickName = styled.div`
  ${tw`ml-2 text-sm`}
  display: inline-block;
`;

const ButtonGroup = styled.div`
  display: flex;
  height: 64px;
  width: 5%;
`;

const UnlockButtonItem = styled.button`
  margin: 0 auto;
  width: fit-content;
  margin-right: 10px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%)
    contrast(83%);
  width: 30px;
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
  width: 20px;
  margin: 0 auto;
  filter: ${(props) => props.filter};
`;

const LikeCount = styled.div`
  margin-left: 7px;
  font-size: 13px;
  line-height: 20px;
`;

const RankingItem = styled.div`
  display: flex;
  width: 100%;
  position: relative;
  padding: 10px;
  align-items: center;
  margin-left: 20px;
`;

const RankingBadge = styled.div`
  position: absolute;
  top: 0px;
  left: -10px;
  width: 40px;
  height: 40px;
  background-color: ${({ rank }) =>
    rank < 4 ? 'rgb(91, 43, 134)' : 'rgb(163, 163, 163)'};
  padding: 5px;
  border-radius: 50%;
  font-size: 20px;
  z-index: 10;
  color: white;
  font-weight: 600;
  /* line-height: 40px; */
`;

const Nag = styled.div`
  text-align: left;
  position: relative;
  width: 100%;
  background-color: white;
  border-radius: 30px;
  padding: 15px 30px;
  margin-right: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const NagContent = styled.div`
  padding-right: 30px;
`;

const ButtonBadge = styled.div`
  position: absolute;
  bottom: 0px;
  right: 10px;
  width: fit-content;
  font-size: 20px;
  z-index: 10;
  cursor: pointer;
`;

const LikeButtonItem = styled.div`
  background-color: white;
  border-radius: 15px;
  padding: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  display: flex;
`;
