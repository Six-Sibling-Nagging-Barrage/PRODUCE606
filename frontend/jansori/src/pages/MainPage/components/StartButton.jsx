import React from 'react';
import tw, { styled } from 'twin.macro';

const StartButton = (props) => {
  const { nagCount } = props;
  return (
    <SpeechBubble>
      <BubbleSpeech>
        <p>잔소리 하러 가기</p>
      </BubbleSpeech>
      <p>현재 작성된 잔소리 총 {nagCount}개</p>
    </SpeechBubble>
  );
};

export default StartButton;

const SpeechBubble = styled.div`
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

const BubbleSpeech = styled.div`
  position: relative;
  background-color: white;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  &:after {
    border-top: 0px solid transparent;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid white;
    content: '';
    position: absolute;
    top: -10px;
    left: 80px;
  }
  &:hover {
    transform: scale(1.02);
  }
`;
