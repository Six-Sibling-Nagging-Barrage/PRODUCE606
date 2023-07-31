import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';

const TodoItem = (props) => {
  const { currentTodo, onTodoStatusChange } = props;

  const handleTodoClick = () => {
    onTodoStatusChange(currentTodo.id);
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
  ${tw`w-96
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
