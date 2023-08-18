import React, { useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import TodoItem from './TodoItem';
import Mark from '../UI/Mark';
import { useRecoilValue, useRecoilState } from 'recoil';
import { focusDateState, todoListState } from '../../states/todo';
import { memberIdState } from '../../states/user';
import { updateTodoComplete, getTodoListByDate, getTodoListOtherByDate } from '../../apis/api/todo';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import FeedBackground from '../UI/FeedBackground';

const TodoList = (props) => {
  const { id } = props;
  const queryClient = useQueryClient();

  const date = useRecoilValue(focusDateState);
  const memberId = useRecoilValue(memberIdState);
  const [todoList, setTodoList] = useRecoilState(todoListState);

  const fetchTodoList = async (date) => {
    if (!date) return;
    let data;
    if (id === memberId) {
      // 내꺼 투두 불러올 때
      data = await getTodoListByDate(date);
    } else {
      // 다른 사람의 투두를 불러오는 부분
      data = await getTodoListOtherByDate(id, date);
    }
    return data?.data;
  };

  const { data } = useQuery(['todoList', todoList], () => fetchTodoList(date));

  const toggleTodoComplete = async (todoId) => {
    const data = await updateTodoComplete(todoId);
    return data;
  };

  const updateTodoCompleteMutation = useMutation((todoId) => toggleTodoComplete(todoId), {
    onMutate: async (todoId) => {
      await queryClient.cancelQueries(['todoList']);
      const prevTodoList = queryClient.getQueryData(['todoList']);
      queryClient.setQueryData(['todoList'], (oldData) => {
        if (!oldData) return null;
        return {
          ...oldData,
          todos: oldData.todos.map((todoItem) => {
            if (todoItem.id === todoId) {
              return {
                ...todoItem,
                finished: !todoItem.finished,
              };
            }
            return todoItem;
          }),
        };
      });
      return prevTodoList;
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['todoList']);
    },
  });

  return (
    <TodoContainer>
      <FeedBackground />
      <div>
        <TodoListWrap>
          <Mark label={'TODO LIST'} />
          <TodoDateWrap>📅 {date}</TodoDateWrap>
        </TodoListWrap>
      </div>
      {data?.todos && data?.todos.length > 0 ? (
        data?.todos.map((todo) => (
          <TodoItem
            key={todo.id}
            id={id}
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
};

export default TodoList;

const TodoListWrap = styled.div`
  ${tw`flex`}
  height : 100%;
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
