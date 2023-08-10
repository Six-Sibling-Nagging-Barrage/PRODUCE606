import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import Modal from '../UI/Modal';
import HashTagItem from '../HashTag/HashTagItem';
import { getTodoDetail } from '../../apis/api/todo';
import moment from 'moment';
import SnackBar from '../UI/SnackBar';

const TodoItem = (props) => {
  const { currentTodo, updateTodoCompleteMutation } = props;
  const [isDetailTodoItem, setIsDetailTodoItem] = useState(false);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const handleTodoClick = () => {
    if (currentTodo.todoAt !== moment().format('YYYY-MM-DD')) {
      setSnackBarMessage('투두 달성 여부는 당일에만 변경 가능합니다!');
      return setShowSnackBar(true);
    }
    updateTodoCompleteMutation(currentTodo.todoId);
  };

  const handleTodoDetail = () => {
    setIsDetailTodoItem(true);
    getTodoDetails(currentTodo.todoId);
    // 현재 todo 상세 내용 저장하는 부분 넣기
  };

  const getTodoDetails = async (todoId) => {
    const data = await getTodoDetail(todoId);
    console.log(data);
    // todo 배열에 저장해서 넘겨주자
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <TodoContainer>
      {isDetailTodoItem && (
        <Modal setIsModalOpen={setIsDetailTodoItem}>
          <div>{currentTodo.content}</div>
        </Modal>
      )}
      <TodoDone>
        <div className='finished' onClick={handleTodoClick}>
          {currentTodo.finished ? '✅' : '❌'}
        </div>
      </TodoDone>
      <TodoContent>
        <TodoContentContainer>{currentTodo.content}</TodoContentContainer>
        <HashTagContent>
          {currentTodo.tags?.map((tag) => {
            return <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />;
          })}
        </HashTagContent>
      </TodoContent>
      <TodoExtendContent>
        {/* input으로 복사 */}
        <button>📋</button>
      </TodoExtendContent>
      <TodoExtendContent>
        {/* 상세 보기 */}
        <button onClick={handleTodoDetail}>📖</button>
      </TodoExtendContent>
      {showSnackBar && <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />}
    </TodoContainer>
  );
};

export default TodoItem;

const TodoContainer = styled.div`
  ${tw`
grid
grid-cols-7
gap-4
border-2
rounded
mt-1
py-2
bg-white`}
`;

const TodoDone = styled.button`
  ${tw`col-start-1
  items-center
  `}
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
  mr-2
items-center
my-auto
`}
`;
