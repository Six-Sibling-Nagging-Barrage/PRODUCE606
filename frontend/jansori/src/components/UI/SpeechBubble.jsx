import React from 'react';
import { styled } from 'twin.macro';

const SpeechBubble = (props) => {
  const { text } = props;

  return <BubbleSpeech>{text}</BubbleSpeech>;
};

export default SpeechBubble;

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
