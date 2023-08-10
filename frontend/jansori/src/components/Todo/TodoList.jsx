import React, { useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import Mark from '../UI/Mark';
import { useRecoilValue } from 'recoil';
import { focusDateState } from '../../states/todo';
import { updateTodoComplete, getTodoListByDate } from '../../apis/api/todo';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

function TodoList() {
  const queryClient = useQueryClient();

  const date = useRecoilValue(focusDateState);

  const fetchTodoList = async (date) => {
    if (!date) return;
    const data = await getTodoListByDate(date);
    return data.data;
  };

  const { data } = useQuery(['todoList', date], () => fetchTodoList(date));

  const toggleTodoComplete = async (todoId) => {
    const data = await updateTodoComplete(todoId);
    return data;
  };

  const updateTodoCompleteMutation = useMutation((todoId) => toggleTodoComplete(todoId), {
    onMutate: async (todoId) => {
      await queryClient.cancelQueries(['todoList']);
      const prevTodoList = queryClient.getQueryData(['todoList']);
      queryClient.setQueryData(['todoList'], (oldData) => {
        const newData = oldData?.todos.map((todoItem) => {
          if (todoItem.todoId === todoId) {
            return {
              ...todoItem,
              finished: !todoItem.finished,
            };
          }
          return todoItem;
        });
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['todoList']);
    },
  });

  return (
    <TodoContainer>
      <div>
        <TodoListWrap>
          <Mark label={'todo List'} />
          <TodoDateWrap>📅 {date}</TodoDateWrap>
        </TodoListWrap>
      </div>
      {data?.todos && data?.todos.length > 0 ? (
        data?.todos.map((todo) => (
          <TodoItem
            key={todo.id}
            currentTodo={todo}
            updateTodoCompleteMutation={updateTodoCompleteMutation.mutate}
          />
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

const TodoListWrap = styled.div`
  ${tw`flex`}
`;

const TodoDateWrap = styled.div`
  ${tw`flex my-auto mr-3`}
  margin-left:auto
`;

const TodoContainer = styled.div`
  ${tw`
  bg-white
    px-3`}
`;
