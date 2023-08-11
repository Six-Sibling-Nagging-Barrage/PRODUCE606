import React from 'react';
import tw, { css, styled } from 'twin.macro';

const Button = (props) => {
  const { children, normal, cancel, onClick, disabled } = props;
  return (
    <ButtonWrap
      onClick={onClick}
      disabled={disabled}
      normal={normal}
      cancel={cancel}
    >
      {children}
    </ButtonWrap>
  );
};

export default Button;

const ButtonWrap = styled.button(({ normal, cancel }) => [
  css`
    border-radius: 50px;
    padding: 8px 18px;
    color: white;
    font-size: 14px;
  `,
  normal &&
    css`
      background-color: rgb(91, 43, 134);
    `,
  cancel &&
    css`
      background-color: rgb(148, 148, 148);
    `,
]);
