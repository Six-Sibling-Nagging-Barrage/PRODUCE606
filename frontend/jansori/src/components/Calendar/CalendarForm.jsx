import React, { useEffect, useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import moment from 'moment';
import tw, { styled } from 'twin.macro';

const CalendarForm = () => {
  const [date, setDate] = useState(new Date());
  const [month, setMonth] = useState(moment().format('MM'));
  const [year, setYear] = useState(moment().format('YYYY'));
  const mark = ['2023-07-25', '2023-08-06', '2023-08-15'];

  const handleActiveStartDateChange = (newStartDate) => {
    const newDate = moment(newStartDate.activeStartDate).format('YYYY-MM');
    setMonth(moment(newDate).format('MM'));
    setYear(moment(newDate).format('YYYY'));
  };

  useEffect(() => {
    // console.log('month', month, 'year', year);
  }, [month, year]);

  return (
    <CalendarCard>
      <StyledCalendar
        className={{}}
        onChange={setDate}
        formatDay={(locale, date) => moment(date).format('DD')} //일 표시 지우기
        value={date}
        minDetail='month'
        maxDetail='month'
        showNeighboringMonth={false}
        tileContent={({ date, view }) => {
          // 날짜 타일에 컨텐츠 추가
          const dateStr = moment(date).format('YYYY-MM-DD');
          if (mark.find((x) => x === dateStr)) {
            return <Dot />;
          }
          return <NotDot />;
        }}
        onActiveStartDateChange={handleActiveStartDateChange} // 활성화된 시작 날짜 변경 시 호출되는 콜백
      />
      <div className='text-gray-500 mt-4'>{moment(date).format('YYYY-MM-DD')}</div>
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
    border
    border-gray-300
    py-5
    rounded-lg
    shadow-sm
    bg-white
    w-2/5
    
  `}
  @media (min-width: 990px) and (max-width: 1200px) {
    width: 50%;
  }
  @media (min-width: 786px) and (max-width: 990px) {
    width: 60%;
  }
  @media (min-width: 680px) and (max-width: 786px) {
    width: 70%;
  }
  @media (max-width: 680px) {
    width: 90%;
  }

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
