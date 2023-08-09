import React from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import Mark from '../UI/Mark';
import { useRecoilState } from 'recoil';
import { useTodoList, focusDateState } from '../../states/todo';
import { updateTodoComplete } from '../../apis/api/todo';

function TodoList() {
  const { todoList, toggleTodo } = useTodoList();
  // const date = focusDateState();
  const [date, setDate] = useRecoilState(focusDateState);
  const handleToggle = async (todoId) => {
    console.log(todoId);
    toggleTodo(todoId);

    // TODO: 토글된 정보를 백엔드로 전송하는 코드 추가
    const updatedTodo = await updateTodoComplete(todoId);
    // fetch 또는 axios 등을 사용하여 백엔드로 전송 가능
  };

  return (
    <TodoContainer>
      <div>
        <Mark label={'todo List'} />
        <span>{date}</span>
      </div>
      {todoList && todoList.length > 0 ? (
        todoList.map((todo) => (
          <TodoItem key={todo.id} onClick={() => handleToggle(todo.todoId)} currentTodo={todo} />
        ))
      ) : (
        <>
          <div>입력된 todo가 없습니다...</div>
        </>
      )}
    </TodoContainer>
  );
}

export default TodoList;
const TodoContainer = styled.div`
  ${tw`
  bg-white
    px-3`}
`;
