import React from 'react';
import tw, { styled } from 'twin.macro';

const Button = (props) => {
  const { label, normal, cancel, onClick, disabled } = props;
  return (
    <ButtonWrap onClick={onClick} disabled={disabled} normal={normal} cancle={cancel}>
      {label}
    </ButtonWrap>
  );
};

export default Button;

const ButtonWrap = styled.button(({ normal, cancel }) => [
  tw`text-lg p-1 px-5 text-sm font-bold rounded-md mt-3`,
  normal && tw`drop-shadow-lg bg-violet-400 text-white`,
  cancel && tw`drop-shadow-lg bg-gray-200 text-slate-500`,
]);
