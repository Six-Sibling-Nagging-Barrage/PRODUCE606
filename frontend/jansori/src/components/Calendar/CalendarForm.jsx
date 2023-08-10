import React, { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import moment from 'moment';
import tw, { styled } from 'twin.macro';
import { getTodoListByDate, getTodoListByDateByMember } from '../../apis/api/todo';
import { useRecoilValue, useRecoilState, useSetRecoilState } from 'recoil';
import { memberIdState } from '../../states/user';
import {
  focusDateState,
  focusYearMonthState,
  todoDaysState,
  todoListState,
} from '../../states/todo';

const CalendarForm = () => {
  const [focusDate, setFocusDate] = useState(new Date());
  const [monthYear, setMonthYear] = useState(moment().format('YYYY-MM'));
  const memberId = useRecoilValue(memberIdState);
  const [date, setDate] = useRecoilState(focusDateState);
  const [yearMonth, setYearMonth] = useRecoilState(focusYearMonthState);
  const [todoDays, setTodoDays] = useRecoilState(todoDaysState);
  const setTodoList = useSetRecoilState(todoListState);

  const handleActiveStartDateChange = async (newStartDate) => {
    const newDate = moment(newStartDate.activeStartDate).format('YYYY-MM');
    setMonthYear(moment(newDate).format('YYYY-MM'));
  };

  // 달 이동할 때 해당하는 api 호출하는 부분
  useEffect(() => {
    setYearMonth(monthYear);
    // TODO: 달 이동할 경우 그 달에 해당하는 TODO 입력된 값들 불러오는 API 호출
    const fetchData = async () => {
      const response = await getTodoListByDateByMember({
        memberId: memberId,
        date: monthYear,
      });
      if (response.code === '200') {
        // 데이터를 받아오는데 성공한 경우
        setTodoDays(response.data.dates);
      }
    };
    fetchData();
  }, [monthYear]);

  // 원하는 날 클릭할 경우 해당하는 todoList 호출하는 api
  useEffect(() => {
    const date = moment(focusDate).format('YYYY-MM-DD');
    setDate(date);
    const fetchData = async () => {
      const response = await getTodoListByDate(date);
      // TODO: recoil로 todoList부분 변경해주는 부분 설정
      if (response.code === '200') {
        setTodoList(response.data.todos);
      }
    };

    fetchData();
  }, [focusDate]);

  useEffect(() => {
    setFocusDate(moment(date).toDate());
  }, [date]);

  return (
    <CalendarCard>
      <StyledCalendar
        onChange={(newFocusDate) => setFocusDate(newFocusDate)}
        formatDay={(locale, focusDate) => moment(focusDate).format('DD')}
        value={moment(focusDate).toDate()}
        minDetail='month'
        maxDetail='month'
        showNeighboringMonth={false}
        tileContent={({ date, view }) => {
          const dateStr = moment(date).format('YYYY-MM-DD');
          if (Array.isArray(todoDays) && todoDays.find((x) => x === dateStr)) {
            return <Dot />;
          }
          return <NotDot />;
        }}
        onActiveStartDateChange={handleActiveStartDateChange}
      />
    </CalendarCard>
  );
};

export default CalendarForm;

const CalendarCard = styled.div``;

const Dot = styled.div`
  ${tw`
    h-3
    w-3
    bg-red-300
    rounded-full
    flex
    mt-1
    mx-auto`}
`;

const NotDot = styled.div`
  ${tw`
    h-3
    w-3
    bg-slate-100
    rounded-full
    flex
    mt-1
    mx-auto`}
`;

const StyledCalendar = styled(Calendar)`
  ${tw`
    border-2
    border-gray-200
    py-5
    rounded-lg
    bg-white
    w-full
  `}

  .react-calendar__navigation button {
    ${tw`
      h-8
      w-8
      flex
      items-center
      justify-center
      border
      rounded-full
      bg-white
      hover:bg-violet-100
    `}
  }

  // 달력 년/월 표시 글시 커스텀
  .react-calendar__navigation__label {
    ${tw`
      font-bold
    `}
  }

  // 요일 section 커스텀 하기
  .react-calendar__month-view__weekdays {
    font-size: 0.9rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 8px;
  }

  .react-calendar__weekdays {
    ${tw`
      h-12
      w-36
      items-center
      justify-center
      border
      rounded-full
      bg-white
      hover:bg-violet-100
    `}
  }

  .react-calendar__month-view__days__day {
    ${tw`
      h-12
      w-36
      p-1
      items-center
      justify-center
      border
      rounded-lg
      bg-white
      hover:bg-violet-100
    `}
  }
  // 날짜 선택 됐을 때 day 타일 커스텀하기
  .react-calendar__tile--active {
    background-color: #b197fc;
  }
`;
