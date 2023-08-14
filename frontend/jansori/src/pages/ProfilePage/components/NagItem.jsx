import React from 'react';
import { styled } from 'twin.macro';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import likeIcon from '../../../assets/like_icon.avif';
import lockIcon from '../../../assets/lock_icon.png';
import sendIcon from '../../../assets/send_icon.png';

const NagItem = (props) => {
  const { isMine, nag, toggleUnlock } = props;

  const handleUnlockNag = async () => {
    toggleUnlock({ nagId: nag.nagId });
  };

  return (
    <>
      {nag && (
        <Nag>
          <Header>
            <HashTagItem editable={false} hashTag={nag.tag} />
            {!isMine && !nag.unlocked && (
              <button onClick={() => handleUnlockNag(nag.nagId)}>
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
              <img src={likeIcon} />
              <span>{nag.likeCount}</span>
            </div>
            <div>
              <img src={sendIcon} />
              <span>{nag.deliveredCount}</span>
            </div>
          </Counter>
        </Nag>
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
  width: 32%;
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

export default NagItem;
