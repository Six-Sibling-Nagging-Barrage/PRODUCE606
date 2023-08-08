import React, { useEffect, useState } from 'react';
import { styled } from 'twin.macro';
import profileImg from '../../../assets/profileImg.png';
import NagCommentItem from './NagCommentItem';
import { createPersonaReaction } from '../../../apis/api/todo';

const PersonaReaction = (props) => {
  const {
    personaReaction,
    setPersonaReaction,
    currentPostId,
    setCurrentPostId,
  } = props;

  const [personaIndex, setPersonaIndex] = useState(-1);

  const handlePersonaHover = (personaId) => {
    setPersonaIndex(personaId - 1);
  };

  const handleClickPersonaReaction = async (personaId, todoPersonaId) => {
    // 캐릭터 반응 api 호출
    const data = await createPersonaReaction({
      todoId: currentPostId,
      todoPersonaId,
    });

    if (!data?.data?.isFirstReaction) return;

    setPersonaReaction((prev) => {
      return prev.map((item) =>
        item.todoPersonaId === data.data.todoPersonaId ? data.data : item
      );
    });
  };

  const handleClosePersona = () => {
    setCurrentPostId(-1);
  };

  return (
    <PersonaReactionWrapper>
      <Header>
        <CloseBtn onClick={handleClosePersona}>X</CloseBtn>
      </Header>
      <PersonaReactionContainer>
        <PersonaCounter>
          {personaReaction.map((reaction) => {
            return (
              <PersonaProfile
                key={reaction.todoPersonaId}
                onMouseEnter={() => {
                  handlePersonaHover(reaction.personaId);
                }}
                onClick={() => {
                  handleClickPersonaReaction(
                    reaction.personaId,
                    reaction.todoPersonaId
                  );
                }}
              >
                <img
                  className="w-10 h-10 rounded-full"
                  src={profileImg} // TODO: 캐릭터 이미지
                  alt="Rounded avatar"
                />
                <CountBadge>
                  {reaction.likeCount < 100 ? reaction.likeCount : '99+'}
                </CountBadge>
              </PersonaProfile>
            );
          })}
        </PersonaCounter>
        {personaIndex === -1 ? (
          <PersonaBio>캐릭터를 클릭해 잔소리를 해주세요.</PersonaBio>
        ) : (
          <PersonaBio>
            <div>{personaInfo[personaIndex].name}</div>
            <div>{personaInfo[personaIndex].bio}</div>
          </PersonaBio>
        )}
        <div>
          {personaReaction.map((reaction) => {
            if (!reaction.content) return;
            return (
              <NagCommentItem
                key={reaction.todoPersonaId}
                isMemberNag={false}
                nag={{
                  nagId: reaction.todoPersonaId,
                  likeCount: reaction.likeCount,
                  content: reaction.content,
                  nagMember: {
                    nickname: personaInfo[reaction.personaId - 1].name,
                    imageUrl: personaInfo[reaction.personaId - 1].img,
                  },
                }}
              />
            );
          })}
        </div>
      </PersonaReactionContainer>
    </PersonaReactionWrapper>
  );
};

const PersonaReactionWrapper = styled.div`
  position: fixed;
  top: 100px;
  right: 30px;
  display: flex;
`;

const PersonaReactionContainer = styled.div`
  width: 350px;
`;

const Header = styled.div`
  position: relative;
  width: 30px;
`;

const CloseBtn = styled.button`
  position: absolute;
  right: 10px;
`;

const PersonaCounter = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin: 15px 0;
`;

const PersonaProfile = styled.div`
  height: 40px;
  &:hover {
    cursor: pointer;
  }
  position: relative;
`;

const CountBadge = styled.div`
  position: absolute;
  top: -10px;
  left: 30px;
  background-color: rgb(255, 121, 121);
  padding: 0 5px;
  border-radius: 15px;
  font-size: 13px;
`;

const PersonaBio = styled.div`
  margin-top: 5px;
  position: relative;
  height: 80px;
  padding: 15px;
  background-color: rgb(238, 238, 238);
  border-radius: 10px;
  &:after {
    content: '';
    position: absolute;
    top: 0;
    left: 5%;
    width: 0;
    height: 0;
    border: 13px solid transparent;
    border-bottom-color: rgb(238, 238, 238);
    border-top: 0;
    border-right: 0;
    margin-left: -6.5px;
    margin-top: -12px;
  }
`;

export default PersonaReaction;

const personaInfo = [
  {
    id: 1,
    name: '캐릭터1',
    img: profileImg,
    bio: '캐릭터1의 설명입니다',
  },
  {
    id: 2,
    name: '캐릭터2',
    img: profileImg,
    bio: '캐릭터2의 설명입니다',
  },
  {
    id: 3,
    name: '캐릭터3',
    img: profileImg,
    bio: '캐릭터3의 설명입니다',
  },
  {
    id: 4,
    name: '캐릭터4',
    img: profileImg,
    bio: '캐릭터4의 설명입니다',
  },
  {
    id: 5,
    name: '캐릭터5',
    img: profileImg,
    bio: '캐릭터5의 설명입니다',
  },
  {
    id: 6,
    name: '캐릭터6',
    img: profileImg,
    bio: '캐릭터6의 설명입니다',
  },
];
