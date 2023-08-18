import React from 'react';
import tw, { styled } from 'twin.macro';

const Card = (props) => {
  const { children } = props;
  return (
    <CardItem>
      {children}
    </CardItem>
  );
};

export default Card;

//카드
const CardItem = styled.div`
  ${tw`
  p-8
bg-white 
border
rounded-lg 
ease-in 
duration-300 
shrink-0`}
`;
