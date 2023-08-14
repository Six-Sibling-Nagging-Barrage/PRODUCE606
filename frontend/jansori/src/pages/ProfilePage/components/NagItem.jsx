import React from 'react';
import { styled } from 'twin.macro';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import likeIcon from '../../../assets/like_icon.avif';
import sendIcon from '../../../assets/send_icon.png';

const NagItem = (props) => {
  const { isMine, nag } = props;

  return (
    <>
      {nag && (
        <Nag>
          <Header>
            <HashTagItem editable={false} hashTag={nag.tag} />
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
  padding-bottom: 10px;
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

export default NagItem;
