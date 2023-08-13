import React, { useEffect, useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';
import TodoForm from '../../../components/TodoForm/TodoForm';
import CalendarForm from '../../../components/Calendar/CalendarForm';
import TodoList from '../../../components/Todo/TodoList';

const TodoHistory = (props) => {
  const { isMine, id } = props;
  const [rightContainerHeight, setRightContainerHeight] = useState(0);
  const leftContainerRef = useRef(null);

  useEffect(() => {
    const leftContainerHeight = leftContainerRef.current.clientHeight;
    setRightContainerHeight(leftContainerHeight);
  }, []);

  return (
    <TodoHistoryContainer>
      <TodoHistoryLeftContainer ref={leftContainerRef}>
        {isMine && <TodoForm />}
        <CalendarForm id={id} />
      </TodoHistoryLeftContainer>
      <TodoHistoryRightContainer style={{ height: `${rightContainerHeight}px` }}>
        <TodoList id={id} />
      </TodoHistoryRightContainer>
    </TodoHistoryContainer>
  );
};

export default TodoHistory;

const TodoHistoryContainer = styled.div`
  ${tw`grid grid-cols-1 gap-1 md:grid-cols-2 md:gap-4
  `}
  height: fit-content
`;

const TodoHistoryLeftContainer = styled.div`
  height: 98.5vh;
`;

const TodoHistoryRightContainer = styled.div`
  background-color: white;
  border-radius: 20px;
  overflow: auto;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;
