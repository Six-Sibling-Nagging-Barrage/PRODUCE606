import React from 'react';
import { styled } from 'twin.macro';

const SpeechBubble = (props) => {
  const { text, normal, isOdd } = props;

  return (
    <BubbleSpeech normal={normal} isOdd={isOdd}>
      {text}
    </BubbleSpeech>
  );
};

export default SpeechBubble;

const BubbleSpeech = styled.div`
  position: relative;
  background-color: white;
  border-radius: 50px;
  padding: 20px;
  margin-bottom: 10px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  // 말풍선 위쪽
  ${(props) =>
    props.normal &&
    `
      font-weight: bold;
      font-size: 18px;
        &:after {
          border-top: 0px solid transparent;
          border-left: 12px solid transparent;
          border-right: 12px solid transparent;
          border-bottom: 12px solid white;
          content: '';
          position: absolute;
          top: -11px;
          left: 40px;
        }
        &:hover {
          transform: scale(1.03);
        }
      `}

  //말풍선 왼쪽(홀수)
${(props) =>
    props.isOdd &&
    `
        &:after {
          border-top:15px solid white;
          border-left: 15px solid transparent;
          border-right: 15px solid transparent;
          border-bottom: 0px solid transparent;
          content:"";
          position:absolute;
          top:8px;
          left:-13px;
        }
      `}
       //말풍선 오른쪽(짝수)
       ${(props) =>
    !props.isOdd &&
    !props.normal &&
    `
        &:after {
          border-top:15px solid white;
          border-left:15px solid transparent;
          border-right: 15px solid transparent;
          border-bottom: 0px solid transparent;
          content:"";
          position:absolute;
          top:8px;
          right:-13px;
        }
      `}
`;
