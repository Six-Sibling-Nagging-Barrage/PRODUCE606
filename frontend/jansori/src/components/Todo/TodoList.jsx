import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import { useRecoilValue } from 'recoil';
import { focusYearMonthState } from '../../states/todo';

const TodoList = () => {
  const [todoList, setTodoList] = useState([]);
  const focusDate = useRecoilValue(focusYearMonthState);

  useEffect(() => {
    setTodoList((prev) => [
      ...prev,
      ...Array.from({ length: 10 }, () => ({
        id: 1,
        finished: false,
        date: '2023.07.31',
        content: '살려주세요!',
      })),
    ]);
  }, []);

  const handleTodoStatusChange = (index) => {
    setTodoList((prev) =>
      prev.map((todo, i) => (i === index ? { ...todo, finished: !todo.finished } : todo))
    );
  };

  return (
    <TodoContainer>
      <div>{focusDate}</div>
      <ul>
        {todoList.map((todo, index) => {
          return (
            <TodoItem
              currentTodo={todo}
              key={todo.id}
              onTodoStatusChange={() => handleTodoStatusChange(index)}
            />
          );
        })}
      </ul>
    </TodoContainer>
  );
};

export default TodoList;

const TodoContainer = styled.div`
  ${tw`
bg-red-400
  p-3
  border-2
  rounded`}
`;
