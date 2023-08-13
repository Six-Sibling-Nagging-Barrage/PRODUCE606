import React from 'react';
import { styled } from 'twin.macro';

const SpeechBubble = (props) => {
  const { children, normal, isodd } = props;

  return (
    <BubbleSpeech normal={normal} isodd={isodd}>
      {children}
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
    props.isodd &&
    `
    &:after {
      content: '';
      position: absolute;
      left: 0;
      top: 50%;
      width: 0;
      height: 0;
      border: 8px solid transparent;
      border-right-color: rgb(255, 255, 255);
      border-left: 0;
      margin-top: -8px;
      margin-left: -6px;
    }
      `}
       //말풍선 오른쪽(짝수)
       ${(props) =>
    !props.isodd &&
    !props.normal &&
    `
    &:before {
      content: '';
      position: absolute;
      right: 0;
      top: 50%;
      width: 0;
      height: 0;
      border: 8px solid transparent;
      border-left-color: rgb(255, 255, 255);
      border-right: 0;
      margin-top: -8px;
      margin-right: -6px;
    }
      `}
`;
