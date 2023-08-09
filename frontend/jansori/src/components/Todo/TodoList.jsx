import React from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import { useRecoilValue, useRecoilState } from 'recoil';
import { focusDateState, todoListState } from '../../states/todo';

const TodoList = () => {
  // const [todoList, setTodoList] = useState([]);
  const [todoList, setTodoList] = useRecoilState(todoListState);

  const handleTodoStatusChange = (index) => {
    setTodoList((prev) =>
      prev.map((todo, i) => (i === index ? { ...todo, finished: !todo.finished } : todo))
    );
  };

  return (
    <TodoContainer>
      <ul>
        {todoList && todoList.length > 0 ? (
          todoList.map((todo, index) => (
            <TodoItem
              currentTodo={todo}
              key={todo.todoId}
              onTodoStatusChange={() => handleTodoStatusChange(index)}
            />
          ))
        ) : (
          <>
            <div>입력된 todo가 존재하지 않아요...</div>
          </>
        )}
      </ul>
    </TodoContainer>
  );
};

export default TodoList;

const TodoContainer = styled.div`
  ${tw`
  bg-white
    p-3`}
`;
