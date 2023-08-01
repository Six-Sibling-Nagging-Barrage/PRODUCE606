import React from 'react';
import tw, { styled } from 'twin.macro';
import SpeechBubble from '../../../components/UI/SpeechBubble';

const StartButton = (props) => {
  const { nagCount } = props;
  return (
    <SpeechBubbleContainer>
      <SpeechBubble text={'잔소리 하러 가기'} />
      <p>현재 작성된 잔소리 총 {nagCount}개</p>
    </SpeechBubbleContainer>
  );
};

export default StartButton;

const SpeechBubbleContainer = styled.div`
  ${tw`
  w-2/3
  sm:w-1/3
  md:w-3/5
  lg:w-2/5
  m-auto
  z-50
`}
  padding-top:50vh;
`;
