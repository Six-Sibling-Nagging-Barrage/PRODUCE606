import React from 'react';
import tw from 'twin.macro';
import { AiOutlineAliwangwang } from 'react-icons/ai';
import { Link } from 'react-router-dom'; // 이 부분에서 react-router-dom의 Link 컴포넌트를 가져옴

const FloatingButton = () => {
  return (
    <FloatingButtonContainer to='nag'>
      <AiOutlineAliwangwang size='100' />
    </FloatingButtonContainer>
  );
};

export default FloatingButton;

const FloatingButtonContainer = tw(Link)`
  fixed
  bottom-10
  right-10
  w-20
  h-20
  bg-red-500
  rounded-full
  flex
  items-center
  justify-center
  text-white
  text-xl
`;
