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
  border-radius: 30px;
  padding: 15px;
  margin-bottom: 10px;
  box-shadow: 5px 5px 8px rgba(0, 0, 0, 0.2);
  // 말풍선 위쪽
  ${(props) =>
    props.normal &&
    `
      font-weight: bold;
      font-size: 20px;
        &:after {
          border-top: 0px solid transparent;
          border-left: 10px solid transparent;
          border-right: 10px solid transparent;
          border-bottom: 10px solid white;
          content: '';
          position: absolute;
          top: -10px;
          left: 40px;
        }
        &:hover {
          transform: scale(1.02);
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
