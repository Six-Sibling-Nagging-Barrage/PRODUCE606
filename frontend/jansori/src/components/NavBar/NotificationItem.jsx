import React from 'react';
import tw, { styled } from 'twin.macro';
const NotificationItem = (props) => {
  const { content, createdAt } = props;

  return (
    <div>
      <NotificationitemWrap>{content}</NotificationitemWrap>
    </div>
  );
};

export default NotificationItem;

const NotificationitemWrap = styled.div`
  z-index: 100;
  border-radius: 10px;
  padding: 1vh;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 1vh;
`;
