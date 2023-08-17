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
    w-full
    h-24
    relative`}
`;

const Background = styled.div`
  ${tw`
    w-full
    h-full
    bg-white
    opacity-50
    absolute`}
  border-radius: 40px;
`;

const BoxContent = styled.div`
  ${tw`
    flex
    justify-center
    items-center
    h-full
    relative`}
  flex-direction: row;
`;

const ValueCount = styled.p`
  ${tw`
  font-bold
  text-2xl
  m-1
  text-xl
  px-2
  lg:text-3xl
  md:text-2xl
  sm:text-xl
`}
  color : #9548c6
`;
