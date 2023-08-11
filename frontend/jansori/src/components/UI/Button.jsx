import React from 'react';
import tw, { css, styled } from 'twin.macro';

const Button = (props) => {
  const { label, normal, cancel, onClick, disabled } = props;
  return (
    <ButtonWrap
      onClick={onClick}
      disabled={disabled}
      normal={normal}
      cancle={cancel}
    >
      {label}
    </ButtonWrap>
  );
};

export default Button;

const ButtonWrap = styled.button(({ normal, cancel }) => [
  css`
    border-radius: 50px;
    padding: 8px 18px;
    color: rgb(86, 86, 86);
    font-weight: bold;
    font-size: 15px;
    box-shadow: 0 0 3px rgba(0, 0, 0, 0.2);
  `,
  normal &&
    css`
      background-color: rgba(255, 255, 255);
    `,
  cancel &&
    css`
      background-color: rgba(183, 183, 183, 0.6);
    `,
]);
