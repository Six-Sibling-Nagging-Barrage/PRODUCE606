import React from "react";

const Button = (props) => {
  console.log("props", props);
  const { label, normal, cancel, onClick, disabled } = props;
  return (
    <button
      className={
        //   기본 버튼
        (normal
          ? "drop-shadow-lg text-lg h-11 px-5 font-bold rounded-md bg-violet-400 text-white"
          : "") +
        // 잔소리 하기 버튼
        (cancel
          ? "drop-shadow-lg text-lg h-11 px-5 font-bold rounded-md bg-gray-200 text-slate-500"
          : "")
      }
      onClick={onClick}
      disabled={disabled}
    >
      {label}
    </button>
  );
};

export default Button;
