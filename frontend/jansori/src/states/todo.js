import { atom, useRecoilState } from 'recoil';
import moment from 'moment';

// ------------------------ 날짜 ------------------------
//클릭하고 있는 날
export const focusDateState = atom({
  key: 'focusDateState',
  default: moment().format('YYYY-MM-DD'),
});

// 클릭하고 있는 달
export const focusYearMonthState = atom({
  key: 'focusYearMonthState',
  default: '',
});

// ------------------------ 투두 ------------------------
// todo달력 todo 있는 날짜 받아온 배열
export const todoDaysState = atom({
  key: 'focusTodoDaysState',
  default: [],
});

// 해당 일 todoList 배열
export const todoListState = atom({
  key: 'todoListState',
  default: [],
});

export function useTodoList() {
  const [todoList, setTodoList] = useRecoilState(todoListState);
  const toggleTodo = (todoId) => {
    setTodoList((prevTodoList) =>
      prevTodoList.map((todo) =>
        todo.id === todoId ? { ...todo, finished: !todo.finished } : todo
      )
    );
  };
  return { todoList, toggleTodo };
}

export const useTodoDetailState = atom({
  key: 'todoDetailState',
  default: [],
});
