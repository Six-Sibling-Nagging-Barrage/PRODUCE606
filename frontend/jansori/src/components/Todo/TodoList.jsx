import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';

const TodoList = () => {
  const [todoList, setTodoList] = useState([]);

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
  ${tw`h-fit
  w-fit
  p-3
  border-2
  rounded`}
`;
