import React, { useState } from 'react';
import { styled } from 'twin.macro';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useRecoilValue, useRecoilState } from 'recoil';
import { updateLikeNag, updateNagUnlock } from '../../apis/api/nag';
import { getTodoDetail } from '../../apis/api/todo';
import { useTodoDetailState } from '../../states/todo';
import { ticketState } from '../../states/user';
import Mark from '../UI/Mark';
import HashTagItem from '../HashTag/HashTagItem';
import NagCommentItem from '../../pages/FeedPage/components/NagCommentItem';
import { personas } from '../../constants/persona';
import SnackBar from '../UI/SnackBar';

const TodoDetail = (props) => {
  const { todoItemDetail, onClose } = props;
  const todoDetailItem = useRecoilValue(useTodoDetailState);
  const [ticket, setTicket] = useRecoilState(ticketState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const queryClient = useQueryClient();
  const { data: todoDetailData } = useQuery(
    ['todoDetailItem', todoDetailItem],
    () => fetchTodoDetail(todoItemDetail.todoId)
  );

  const fetchTodoDetail = async (todoId) => {
    const data = await getTodoDetail(todoId);
    return data.data;
  };

  //좋아요 처리를 위한 useMutation
  const updateLikeMutation = useMutation((nagId) => toggleLike(nagId), {
    onMutate: async (nagId) => {
      await queryClient.cancelQueries(['todoDetailItem']);
      const prevTodoDetail = queryClient.getQueryData(['todoDetailItem']);
      queryClient.setQueryData(
        ['todoDetailItem', todoDetailItem],
        (oldData) => {
          const updatedNag = {
            ...oldData.nag,
            isLiked: !oldData.nag.isLiked,
            likeCount: oldData.nag.isLiked
              ? oldData.nag.likeCount - 1
              : oldData.nag.likeCount + 1,
          };
          return {
            ...oldData,
            nag: updatedNag,
          };
        }
      );
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['todoDetailItem']);
    },
  });

  const toggleLike = async (nag) => {
    // 잔소리 좋아요 api 호출
    const data = await updateLikeNag(nag.nagId);
    return data.data;
  };

  const toggleUnlock = async (nag) => {
    if (ticket < 1) {
      setSnackBarMessage(
        '티켓이 부족해 잔소리를 열어볼 수 없어요! 잔소리를 작성하면 티켓을 얻을 수 있어요.'
      );
      return setShowSnackBar(true);
    }
    // 잔소리 초성 해제 api
    const data = await updateNagUnlock(nag.nagId);
    if (data.code === '200') {
      setTicket(data.data.ticketCount);
      setSnackBarMessage('티켓 1개를 소모해 잔소리를 열었어요.');
      setShowSnackBar(true);
    }
    return data;
  };

  const updateUnlockMutation = useMutation((nagId) => toggleUnlock(nagId), {
    onMutate: async (nagId) => {
      await queryClient.cancelQueries(['todoDetailItem']);
      const prevTodoDetail = queryClient.getQueryData(['todoDetailItem']);
      queryClient.setQueryData(
        ['todoDetailItem', todoDetailItem],
        (oldData) => {
          const updatedNag = {
            ...oldData.nag,
            unlocked: true,
          };
          return {
            ...oldData,
            nag: updatedNag,
          };
        }
      );
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['todoDetailItem']);
    },
  });

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <>
      <Background onClick={onClose} />
      <TodoItemDetailTodoContainer>
        <Header>
          <MarkWrap>
            <Mark label={'Todo Detail'} />
          </MarkWrap>
          <DateHeader>{todoDetailData?.todoAt}</DateHeader>
        </Header>
        <TodoWrap>
          <TodoFinishedWrap>
            {todoDetailData?.finished ? '✅' : '❌'}
          </TodoFinishedWrap>
          <div>{todoDetailData?.content}</div>
          <HashTagContent>
            {todoDetailData?.tags?.map((tag) => {
              return <HashTagItem key={tag.tagId} hashTag={tag} />;
            })}
          </HashTagContent>
        </TodoWrap>
        <NagListWrap>
          {todoDetailData && (
            <NagWrap>
              {todoDetailData.nag && (
                <NagCommentItem
                  key={todoDetailData.nag.nagId}
                  isMemberNag={true}
                  todoId={todoDetailData.todoId}
                  nag={todoDetailData.nag}
                  toggleLike={updateLikeMutation.mutate}
                  toggleUnlock={updateUnlockMutation.mutate}
                />
              )}
              {todoDetailData?.personas?.map((persona) => {
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
          )}
        </NagListWrap>
      </TodoItemDetailTodoContainer>
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </>
  );
};

export default TodoDetail;

const Background = styled.div`
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.3);
  z-index: 30;
`;

const TodoItemDetailTodoContainer = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 38vw;
  height: 65vh;
  border-radius: 10px;
  margin: 0 auto;
  background-color: #fff;
  box-shadow: 5px 5px 20px #928f8f;
  display: flex;
  z-index: 30;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
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
