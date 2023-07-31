import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';

const TodoList = () => {
  const [todoList, setTodoList] = useState([]);

  useEffect(() => {
    setTodoList((prev) => [
      ...prev,
      ...Array.from({ length: 10 }, () => ({
        id: getId(),
        finished: false,
        date: '2023.07.31',
        content: '살려주세요!',
      })),
    ]);
  }, []);

  const TodoStatusChangeHandler = (todoID) => {
    setTodoList((prev) =>
      prev.map((todo) => (todo.id === todoID ? { ...todo, finished: !todo.finished } : todo))
    );
  };

  return (
    <TodoContainer>
      <ul>
        {todoList.map((todo) => {
          return (
            <TodoItem
              currentTodo={todo}
              key={todo.id}
              onTodoStatusChange={TodoStatusChangeHandler}
            />
          );
        })}
      </ul>
    </TodoContainer>
  );
};

export default TodoList;

let id = 0;
function getId() {
  return id++;
}

const TodoContainer = styled.div`
  ${tw`h-fit
  w-fit
  p-3
  border-2
  rounded`}
`;

const TodoItemContainer = styled.div`
  ${tw`border-2`}
`;
