import React from 'react';
import { styled } from 'twin.macro';

const TodoContent = () => {
  return (
    <StyledDiv>
      <div>✅</div>
      <div>페이지 언능 만들기 ! ! !</div>
      <div>해시태그 자리</div>
    </StyledDiv>
  );
};

const StyledDiv = styled.div`
  text-align: center;
`;

export default TodoContent;
