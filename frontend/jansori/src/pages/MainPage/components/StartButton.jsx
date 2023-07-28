import React from "react";
import tw, { styled } from "twin.macro";

const StartButton = (props) => {
  const { nagCount } = props;
  return (
    <SpeechBubblt>
      <BubbleSpeech>
        <p>잔소리 하러 가기</p>
      </BubbleSpeech>
      <p>현재 작성된 잔소리 총 {nagCount}개</p>
    </SpeechBubblt>
  );
};

export default StartButton;

const SpeechBubblt = styled.div`
  ${tw`
  snap-center
  w-6/12
  h-fit
`}
`;

const BubbleSpeech = styled.div`
  background-color: white;
  border-radius: 10px;
  padding: 15px;
  margin-bottom: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  &&::after {
    border-top: 0px solid transparent;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid white;
    content: "";
    position: absolute;
    top: -10px;
    left: 80px;
  }
`;
