import React, { useEffect, useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';
import TodoForm from '../../../components/TodoForm/TodoForm';
import CalendarForm from '../../../components/Calendar/CalendarForm';
import TodoList from '../../../components/Todo/TodoList';

const TodoHistory = () => {
  const [rightContainerHeight, setRightContainerHeight] = useState(0);
  const leftContainerRef = useRef(null);

  useEffect(() => {
    const leftContainerHeight = leftContainerRef.current.clientHeight;
    setRightContainerHeight(leftContainerHeight);
  }, []);

  return (
    <TodoHistoryContainer>
      <TodoHistoryLeftContainer ref={leftContainerRef}>
        <TodoForm />
        <CalendarForm />
      </TodoHistoryLeftContainer>
      <TodoHistoryRightContainer style={{ height: `${rightContainerHeight}px` }}>
        <TodoList />
      </TodoHistoryRightContainer>
    </TodoHistoryContainer>
  );
};

export default TodoHistory;

const TodoHistoryContainer = styled.div`
  ${tw`grid grid-cols-5 gap-4
  `}
`;

const TodoHistoryLeftContainer = styled.div`
  ${tw`col-span-2`}
`;

const TodoHistoryRightContainer = styled.div`
  ${tw`col-span-3 bg-red-100 overflow-auto`}
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;
