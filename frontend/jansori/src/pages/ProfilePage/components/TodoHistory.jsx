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
  ${tw`grid grid-cols-2 gap-4
  `}
`;

const TodoHistoryLeftContainer = styled.div`
  ${tw``}
`;

const TodoHistoryRightContainer = styled.div`
  ${tw`overflow-auto bg-white border-2 rounded-lg`}
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none;
`;
