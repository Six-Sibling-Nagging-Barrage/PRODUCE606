import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import NagCommentItem from './NagCommentItem';
import { createPersonaReaction } from '../../../apis/api/todo';
import { personas } from '../../../constants/persona';
import { useImageErrorHandler } from '../../../hooks/useImageErrorHandler';
import { Link } from 'react-router-dom';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import { altImageUrl } from '../../../constants/image';

const PersonaReaction = (props) => {
  const {
    personaReaction,
    setPersonaReaction,
    currentPostId,
    setCurrentPostId,
    currentPost,
  } = props;

  const [personaIndex, setPersonaIndex] = useState(-1);

  const handleImgError = useImageErrorHandler();

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
        <PostContainer>
          <PostHeader>
            <Link
              to={`/profile?id=${encodeURIComponent(
                currentPost.member.memberId
              )}`}
            >
              <ProfileImage
                src={
                  currentPost.member.imageUrl
                    ? currentPost.member.imageUrl
                    : altImageUrl
                }
                onError={handleImgError}
              />
            </Link>
            <div>
              <WriterName>{currentPost.member.nickname}</WriterName>
              <CreateDate>{currentPost.todoAt}</CreateDate>
            </div>
          </PostHeader>
          <TodoContent>
            <div className="todo">{currentPost.content}</div>
            <HashTagContainer>
              {currentPost.tags?.map((tag) => {
                return (
                  <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />
                );
              })}
            </HashTagContainer>
          </TodoContent>
        </PostContainer>
        <PersonaContainer>
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
        </PersonaContainer>
      </PersonaReactionContainer>
    </PersonaReactionWrapper>
  );
};

const PersonaReactionWrapper = styled.div`
  position: fixed;
  top: 72px;
  right: 20px;
  display: flex;
  max-height: 80vh;
`;

const PersonaReactionContainer = styled.div`
  width: 400px;
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

const PersonaContainer = styled.div`
  max-height: 400px;
  overflow: auto;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
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

const PostContainer = styled.div`
  ${tw`p-3 pb-1.5`}
  border-radius: 20px;
  margin: 0 auto;
  background-color: white;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.3);
  font-size: 14px;
`;

const PostHeader = styled.header`
  ${tw`flex gap-4`}
  position: relative;
`;

const ProfileImage = styled.img`
  object-fit: cover;
  width: 35px;
  height: 35px;
  ${tw`max-w-full rounded-full`}
`;

const WriterName = styled.div`
  text-align: left;
  font-size: 13px;
`;

const CreateDate = styled.div`
  ${tw`text-slate-400`}
  text-align:left;
  font-size: 12px;
`;

const TodoContent = styled.div`
  text-align: center;
  margin: 10px;
  & .todo {
    margin: 10px 0;
  }
`;

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 0 auto;
  display: flex;
`;

export default PersonaReaction;
