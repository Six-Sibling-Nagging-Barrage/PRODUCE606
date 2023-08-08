import { atom } from 'recoil';
import moment from 'moment';

// ------------------------ 날짜 ------------------------
//클릭하고 있는 날
export const focusDateState = atom({
  key: 'focusDateState',
  default: '',
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
