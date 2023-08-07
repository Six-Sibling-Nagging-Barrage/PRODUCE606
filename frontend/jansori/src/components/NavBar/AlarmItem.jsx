import React from 'react';
import tw, { styled } from 'twin.macro';

const AlarmItem = (props) => {
  const { text } = props;
  return <AlarmItemContainer>{text}</AlarmItemContainer>;
};

export default AlarmItem;

const AlarmItemContainer = styled.div`
  ${tw`w-11/12
  m-auto
  bg-gray-100
  `}
`;
