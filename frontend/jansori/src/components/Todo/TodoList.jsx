import React from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import { useRecoilValue, useRecoilState } from 'recoil';
import { focusDateState, todoListState } from '../../states/todo';

const TodoList = () => {
  // const [todoList, setTodoList] = useState([]);
  const focusDate = useRecoilValue(focusDateState);
  const [todoList, setTodoList] = useRecoilState(todoListState);

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
              key={todo.index}
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
