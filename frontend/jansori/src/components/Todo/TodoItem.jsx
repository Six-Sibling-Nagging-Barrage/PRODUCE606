import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { getTodoDetail } from '../../apis/api/todo';
import { memberIdState } from '../../states/user';
import { useTodoDetailState, todoInputFormContent, todoInputFormHashTag } from '../../states/todo';
import moment from 'moment';
// import Modal from '../UI/Modal';
import HashTagItem from '../HashTag/HashTagItem';
import SnackBar from '../UI/SnackBar';
import TodoDetail from './TodoDetail';
import detailIcon from '../../assets/detail_icon.png';
import copyIcon from '../../assets/copy_icon.webp';
import todoDone from '../../assets/todo_done.png';
import todoNotDone from '../../assets/todo_not_done.png';

const TodoItem = (props) => {
  const { currentTodo, updateTodoCompleteMutation, id } = props;
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const memberId = useRecoilValue(memberIdState);
  const [isDetailTodoItem, setIsDetailTodoItem] = useState(false);
  const [todoItemDetail, setTodoItemDetail] = useRecoilState(useTodoDetailState);
  const setTodoInputFormContent = useSetRecoilState(todoInputFormContent);
  const setTodoInputFormHashTag = useSetRecoilState(todoInputFormHashTag);

  const handleTodoClick = () => {
    if (memberId !== id) {
      setSnackBarMessage('다른 사람 todo는 변경 못해요..!');
      return setShowSnackBar(true);
    }
    if (currentTodo.todoAt !== moment().format('YYYY-MM-DD')) {
      setSnackBarMessage('투두 달성 여부는 당일에만 변경 가능합니다!');
      return setShowSnackBar(true);
    }
    updateTodoCompleteMutation(currentTodo.todoId);
  };

  const handleTodoDetail = () => {
    getTodoDetails(currentTodo.todoId);
  };

  const getTodoDetails = async (todoId) => {
    const data = await getTodoDetail(todoId);
    setTodoItemDetail(data.data);
    setIsDetailTodoItem(true);
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  const handleModalOpen = () => {
    setIsDetailTodoItem(false);
  };

  const handleTodoCopy = () => {
    setTodoInputFormContent(currentTodo.content);
    setTodoInputFormHashTag(currentTodo.tags);
    setSnackBarMessage('투두가 복사되었습니다.');
    return setShowSnackBar(true);
  };

  return (
    <TodoContainer>
      {isDetailTodoItem && (
        <TodoDetail
          onClose={handleModalOpen}
          todoItemDetail={todoItemDetail}
          setIsDetailTodoItem={setIsDetailTodoItem}
        />
      )}
      <TodoDone>
        <div className='finished' onClick={handleTodoClick}>
          {currentTodo.finished ? (
            <TodoToggleButton src={todoDone} />
          ) : (
            <TodoToggleButton src={todoNotDone} />
          )}
        </div>
      </TodoDone>
      <TodoContent>
        <TodoContentContainer>{currentTodo?.content}</TodoContentContainer>
        <HashTagContent>
          {currentTodo?.tags?.map((tag) => {
            return <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />;
          })}
        </HashTagContent>
      </TodoContent>
      <TodoExtendContent>
        {/* input으로 복사 */}
        {memberId === id && (
          <button onClick={handleTodoCopy}>
            <img src={copyIcon} width='25px' />
          </button>
        )}
      </TodoExtendContent>
      <TodoExtendContent>
        {/* 상세 보기 */}
        <button onClick={handleTodoDetail} todoItemDetail={todoItemDetail}>
          <img src={detailIcon} width='25px' />
        </button>
      </TodoExtendContent>
      {showSnackBar && <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />}
    </TodoContainer>
  );
};

export default TodoItem;

const TodoContainer = styled.div`
  border-radius: 20px;
  background-color: white;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
  ${tw`
grid
grid-cols-7
border-2
mt-1
py-2
bg-white`}
`;

const TodoDone = styled.button`
  ${tw`col-start-1
  items-center
  `}
`;

const TodoToggleButton = styled.img`
  width: 40px;
  margin: 0 auto;
`;

const TodoContent = styled.div`
  ${tw`col-span-4
  text-left`}
`;

const TodoContentContainer = styled.div`
  ${tw`ml-1 mt-1 mb-2`}
`;

const HashTagContent = styled.div`
  ${tw`text-xs flex`}
`;

const TodoExtendContent = styled.div`
  ${tw`col-span-1
  rounded-lg
  mr-1
items-center
my-auto
`}
`;
