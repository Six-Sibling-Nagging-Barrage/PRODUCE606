import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import moment from "moment";
import tw, { styled } from "twin.macro";

const CalendarForm = () => {
  const [value, onChange] = useState(new Date());
  const [mark, setMark] = useState(["2023-07-31", "2023-07-28"]);

  return (
    <CalendarCard>
      <Calendar
        onChange={onChange} //useState 로 포커스 변경 시 현재 날짜 받아오는 부분
        formatDay={(locale, date) => moment(date).format("DD")} //'일' 제외하고 숫자만 보이도록
        value={value}
        minDetial="month" //상단 네비게이션에서 월 단위만 보이게
        maxDeatil="month"
        showNeighboringMonth={false} //이전 이후 달의 날짜는 보이지 않도록 설정
        tileContent={({ date, view }) => {
          const dateStr = moment(date).format("YYYY-MM-DD");
          if (mark.includes(dateStr)) {
            return <Dot />;
          }
        }}
      />
      {/* 해당 클릭한 날짜 값 받아오는 부분 */}
      {/* <div className="text-gray-500 mt-4">{moment(value).format("YYYY년 MM월 DD일")}</div> */}
    </CalendarCard>
  );
};

export default CalendarForm;

const CalendarCard = styled.div`
  ${tw``}
`;

const Dot = styled.div`
  ${tw`
  h-3
  w-3
  bg-red-300
  rounded-full
  flex
  mx-auto`}
`;
