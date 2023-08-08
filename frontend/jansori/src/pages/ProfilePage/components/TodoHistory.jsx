import React from 'react';
import tw, { styled } from 'twin.macro';
import TodoForm from '../../../components/TodoForm/TodoForm';
import CalendarForm from '../../../components/Calendar/CalendarForm';

const TodoHistory = () => {
  return (
    <TodoHistoryContainer>
      <TodoHistoryLeftContainer>
        <TodoForm />
        <CalendarForm />
      </TodoHistoryLeftContainer>
      <TodoHistoryRightContainer>TodoHistoryRightContainer</TodoHistoryRightContainer>
    </TodoHistoryContainer>
  );
};

export default TodoHistory;

const TodoHistoryContainer = styled.div`
  ${tw`grid grid-cols-5 gap-4`}
`;

const TodoHistoryLeftContainer = styled.div`
  ${tw`col-span-2 bg-red-100`}
`;

const TodoHistoryRightContainer = styled.div`
  ${tw`col-span-3 bg-red-100`}
`;
