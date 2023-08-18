import React, { useState } from 'react';
import { styled } from 'twin.macro';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import sendIcon from '../../../assets/send_icon.png';
import SnackBar from '../../../components/UI/SnackBar';
import AlertModal from '../../../components/UI/AlertModal';

const NagItem = (props) => {
  const { isMine, nag, toggleLike, toggleUnlock, width } = props;

  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleLikeClick = () => {
    if (isMine) return;
    if (!nag.unlocked) {
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
      {nag && (
        <Nag width={width}>
          <Header>
            <HashTagItem editable={false} hashTag={nag.tag} />
            {!isMine && !nag.unlocked && (
              <button onClick={handleClickUnlock}>
                <UnlockImg src={lockIcon} />
              </button>
            )}
          </Header>
          {isMine ? (
            <NagContent>{nag.content}</NagContent>
          ) : (
            <NagContent>{nag.unlocked ? nag.content : nag.preview}</NagContent>
          )}
          <Counter>
            <div>
              <button onClick={handleLikeClick}>
                {isMine || nag.isLiked ? (
                  <LikeImg src={likeIcon} />
                ) : (
                  <LikeImg
                    src={likeIcon}
                    filter="invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);"
                  />
                )}
              </button>
              <span>{nag.likeCount}</span>
            </div>
            <div>
              <img src={sendIcon} />
              <span>{nag.deliveredCount}</span>
            </div>
          </Counter>
        </Nag>
      )}
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

const Header = styled.div`
  display: flex;
  justify-content: space-between;
`;

const Nag = styled.div`
  border-radius: 10px;
  background-color: white;
  padding: 20px;
  width: ${({ width }) => `${width}`};
  margin-bottom: 10px;
  height: fit-content;
  position: relative;
`;

const NagContent = styled.div`
  width: 100%;
  text-align: center;
  padding: 10px;
  margin-bottom: 30px;
`;

const Counter = styled.div`
  display: flex;
  & div {
    display: flex;
    margin-right: 20px;
  }
  & img {
    width: 20px;
    height: 20px;
    margin-right: 7px;
  }
  position: absolute;
  bottom: 20px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%)
    contrast(83%);
  width: 20px;
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
`;

export default NagItem;
