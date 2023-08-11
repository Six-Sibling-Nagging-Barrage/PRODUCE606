import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import profileImg from '../../../assets/profileImg.png';
import NagCommentItem from './NagCommentItem';
import { createPersonaReaction } from '../../../apis/api/todo';
import { altImageUrl } from '../../../constants/image';
import { personas } from '../../../constants/persona';

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

  const handleImgError = (e) => {
    e.target.src = altImageUrl;
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
                <PersonaImg
                  src={personas[reaction.personaId - 1].imgUrl}
                  onError={handleImgError}
                />
                {/* <CountBadge>
                  {reaction.likeCount < 100 ? reaction.likeCount : '99+'}
                </CountBadge> */}
              </PersonaProfile>
            );
          })}
        </PersonaCounter>
        {personaIndex === -1 ? (
          <PersonaBio personaId={-1}>
            캐릭터를 클릭해 잔소리를 해주세요.
          </PersonaBio>
        ) : (
          <PersonaBio personaId={personaIndex}>
            <div>{personas[personaIndex].name}</div>
            <div>{personas[personaIndex].bio}</div>
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
                    nickname: personas[reaction.personaId - 1].name,
                    imageUrl: personas[reaction.personaId - 1].imgUrl,
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
  top: 85px;
  right: 20px;
  display: flex;
`;

const PersonaReactionContainer = styled.div`
  width: fit-content;
  background-color: white;
  padding: 10px 15px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
`;

const Header = styled.div`
  position: relative;
  width: 30px;
`;

const CloseBtn = styled.button`
  position: absolute;
  right: 10px;
`;

const PersonaImg = styled.img`
  ${tw`w-16 h-16`}
`;

const PersonaCounter = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin: 10px 0 20px 0;
`;

const PersonaProfile = styled.div`
  &:hover {
    cursor: pointer;
  }
  position: relative;
`;

const CountBadge = styled.div`
  position: absolute;
  top: 0px;
  left: 50px;
  background-color: rgb(255, 121, 121);
  padding: 0 5px;
  border-radius: 15px;
  font-size: 13px;
`;

const PersonaBio = styled.div`
  position: relative;
  width: 400px;
  height: fit-content;
  padding: 15px;
  background-color: rgb(244, 244, 244);
  border-radius: 10px;
  & > div:first-child {
    font-size: 15px;
    font-weight: bold;
  }
  & > div:nth-child(2) {
    font-size: 14px;
  }
  &:after {
    content: '';
    position: absolute;
    top: 0;
    ${({ personaId }) => {
      if (personaId === -1 || personaId === 0) return 'left: 7%;';
      else if (personaId === 1) return 'left: 24%;';
      else if (personaId === 2) return 'left: 41%;';
      else if (personaId === 3) return 'left: 57%;';
      else if (personaId === 4) return 'left: 74%;';
      else if (personaId === 5) return 'left: 90%;';
    }};
    width: 0;
    height: 0;
    border: 13px solid transparent;
    border-bottom-color: rgb(244, 244, 244);
    border-top: 0;
    border-right: 0;
    margin-left: -6.5px;
    margin-top: -12px;
  }
`;

export default PersonaReaction;
