import React from 'react';
import tw, { styled } from 'twin.macro';

const CountBox = (props) => {
  const { startSentence, count, endSentence } = props;
  return (
    <BoxContainer>
      <Background />
      <BoxContent>
        <p>{startSentence}</p>
        <ValueCount>{count}</ValueCount>
        <p>{endSentence}</p>
      </BoxContent>
    </BoxContainer>
  );
};

export default CountBox;

const BoxContainer = styled.div`
  ${tw`
    w-64
    h-40`}
`;

const Background = styled.div`
  ${tw`
    rounded
    w-64
    h-40
    bg-white
    fixed
    z-0
    opacity-50`}
`;

const BoxContent = styled.div`
  ${tw`
    flex
    flex-col
    justify-center
    items-center
    h-full
    z-50
    relative`}
`;

const ValueCount = styled.p`
  ${tw`
  font-bold
  text-2xl
  m-1
  text-green-500
`}
`;
