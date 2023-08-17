import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import tw, { css, styled } from 'twin.macro';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../../states/user';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import SpeechBubble from '../../../components/UI/SpeechBubble';
import SnackBar from '../../../components/UI/SnackBar';
import { useImageErrorHandler } from '../../../hooks/useImageErrorHandler';
import { altImageUrl } from '../../../constants/image';
import AlertModal from '../../../components/UI/AlertModal';

const NagRankingItem = (props) => {
  const { nag, isodd, toggleLike, toggleUnlock, rank } = props;
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
        <Ranking>{rank}</Ranking>
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
            {memberId === nag.nagMember.memberId || nag.unlocked
              ? nag.content
              : nag.preview}
          </NagContent>
        </Nag>
        <ButtonGroup>
          {memberId === nag.nagMember.memberId ||
            (!nag.unlocked && (
              <ButtonItem onClick={handleClickUnlock}>
                <UnlockImg src={lockIcon} />
              </ButtonItem>
            ))}
          <ButtonItem onClick={handleLikeClick}>
            {nag.isLiked ? (
              <LikeImg src={likeIcon} />
            ) : (
              <LikeImg
                src={likeIcon}
                filter="invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);"
              />
            )}
            <LikeCount>{nag.likeCount}</LikeCount>
          </ButtonItem>
        </ButtonGroup>
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
  width: 10%;
`;

const ButtonItem = styled.button`
  margin-right: 5px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%)
    contrast(83%);
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

const RankingItem = styled.div`
  display: flex;
  width: 100%;
  position: relative;
  padding: 10px;
  align-items: center;
`;

const Ranking = styled.div`
  font-size: 50px;
  font-weight: 600;
  margin-right: 15px;
  width: 5%;
  text-align: left;
`;

const Nag = styled.div`
  text-align: left;
  position: relative;
  width: 85%;
  background-color: white;
  padding: 15px 20px;
  border-radius: 30px;
  margin-right: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const NagContent = styled.div`
  padding-right: 30px;
  font-size: 18px;
`;
