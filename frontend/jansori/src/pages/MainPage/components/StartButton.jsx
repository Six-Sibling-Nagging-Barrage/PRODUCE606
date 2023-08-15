import React from 'react';
import tw, { styled } from 'twin.macro';
import { Link } from 'react-router-dom';
import SpeechBubble from '../../../components/UI/SpeechBubble';

const StartButton = (props) => {
  const { nagCount } = props;
  return (
    <SpeechBubbleContainer>
      <Link to='/nag'>
        <SpeechBubble normal='true'>잔소리 하러 가기</SpeechBubble>
      </Link>
      {nagCount !== -1 && <TotalNagCount>현재 작성된 잔소리 총 {nagCount}개</TotalNagCount>}
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
  mx-auto
  z-30
`}
`;

const TotalNagCount = styled.p`
  ${tw`text-zinc-500	`}
`;
