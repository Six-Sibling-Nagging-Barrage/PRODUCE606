import React from 'react';
import tw, { styled } from 'twin.macro';
const NotificationItem = (props) => {
  const { content, createdAt, lastReadAt } = props;
  const createdDate = new Date(
    createdAt[0],
    createdAt[1],
    createdAt[2],
    createdAt[3],
    createdAt[4],
    createdAt[5]
  );
  const lastReadDate = new Date(
    lastReadAt[0],
    lastReadAt[1],
    lastReadAt[2],
    lastReadAt[3],
    lastReadAt[4],
    lastReadAt[5]
  );

  const diffMSec = lastReadDate.getTime() - createdDate.getTime();

  return (
    <div>
      <NotificationitemWrap>
        {diffMSec < 0 && <Circle />}
        <ContentWrap>{content}</ContentWrap>
      </NotificationitemWrap>
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
  position: relative;
  line-height: 20px;
`;

const ContentWrap = styled.div`
  width: 90%;
  margin: 0 auto;
  padding: 5px 0;
`;

const Circle = styled.div`
  z-index: 105;
  position: absolute;
  left: 2%;
  top: 50%;
  transform: translateY(-50%);
  &:after {
    content: '';
    display: inline-block;
    width: 7px;
    height: 7px;
    background-color: #c870ee;
    border-radius: 50%;
    // animation: blink 1s ease-in-out infinite alternate;
  }

  @keyframes blink {
    0% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }
`;
