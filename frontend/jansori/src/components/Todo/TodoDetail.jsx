import React, { useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import Mark from '../UI/Mark';
import HashTagItem from '../HashTag/HashTagItem';
import NagCommentItem from '../../pages/FeedPage/components/NagCommentItem';
import { personas } from '../../constants/persona';

const TodoDetail = (props) => {
  const { todoItemDetail } = props;

  return (
    <TodoItemDetailWrap>
      <TodoItemDetailTodoContainer>
        <Header>
          <MarkWrap>
            <Mark label={'Todo Detail'} />
          </MarkWrap>
          <DateHeader>{todoItemDetail?.todoAt}</DateHeader>
        </Header>
        <TodoWrap>
          <TodoFinishedWrap>{todoItemDetail?.finished ? '✅' : '❌'}</TodoFinishedWrap>
          <div>{todoItemDetail?.content}</div>
          <HashTagContent>
            {todoItemDetail?.tags?.map((tag) => {
              return <HashTagItem key={tag.tagId} hashTag={tag} />;
            })}
          </HashTagContent>
        </TodoWrap>
        <NagListWrap>
          <NagWrap>
            <NagCommentItem
              key={todoItemDetail?.nag.nagId}
              isMemberNag={true}
              todoId={todoItemDetail?.todoId}
              nag={todoItemDetail?.nag}
            />
            {todoItemDetail?.personas?.map((persona) => {
              if (!persona.content) return;
              return (
                <NagCommentItem
                  key={persona.todoPersonaId}
                  isMemberNag={false}
                  nag={{
                    nagId: persona.todoPersonaId,
                    likeCount: persona.likeCount,
                    content: persona.content,
                    nagMember: {
                      nickname: personas[persona.personaId - 1].name,
                      imageUrl: personas[persona.personaId - 1].imgUrl,
                    },
                  }}
                />
              );
            })}
          </NagWrap>
        </NagListWrap>
      </TodoItemDetailTodoContainer>
    </TodoItemDetailWrap>
  );
};

export default TodoDetail;

const TodoItemDetailWrap = styled.div`
  width: 40vw;
`;

const TodoItemDetailTodoContainer = styled.div`
  width: 38vw;
  height: 65vh;
  border-radius: 10px;
  margin: 0 auto;
  background-color: #fff;
  box-shadow: 5px 5px 20px #928f8f;
  display: flex;
  flex-direction: column;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  background-color: #d3c3ff;
  height: 25px;
  border-radius: 10px 10px 0px 0px;
  padding: 0 10px;
`;

const MarkWrap = styled.div`
  display: flex;
  margin-top: 20px;
  align-items: center;
`;

const DateHeader = styled.div`
  display: flex;
  align-items: center;
  color: white;
  font-weight: bold;
  font-size: 14px;
`;

const HashTagContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin-top: 1vh;
  margin-bottom: 3vh;
`;

const TodoWrap = styled.div`
  margin-top: 5vh;
`;

const TodoFinishedWrap = styled.div`
  margin-bottom: 2vh;
`;

const NagListWrap = styled.div`
  flex-grow: 1;
  overflow: auto;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const NagWrap = styled.div`
  width: 95%;
  margin: 0 auto;
`;
