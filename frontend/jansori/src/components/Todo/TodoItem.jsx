import React from 'react';
import tw, { styled } from 'twin.macro';

const TodoItem = (props) => {
  const { currentTodo, onTodoStatusChange, index } = props;

  const handleTodoClick = () => {
    onTodoStatusChange(index);
  };

  return (
    <TodoContainer>
      <TodoDone onClick={handleTodoClick}>
        <div className='finished'>{currentTodo.finished ? '✅' : '❌'}</div>
      </TodoDone>
      <TodoContent>
        <div className='todo'>{currentTodo.content}</div>
        <HashTagContent>해시태그</HashTagContent>
      </TodoContent>
    </TodoContainer>
  );
};

export default TodoItem;

const TodoContainer = styled.div`
  ${tw`
grid
grid-cols-5
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

const HashTagContent = styled.div`
  ${tw`text-xs`}
`;
