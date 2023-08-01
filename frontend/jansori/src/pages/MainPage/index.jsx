import React, { useState, useEffect } from 'react';
import styled, { keyframes } from 'styled-components';
import StartButton from './components/StartButton';
import Background from '../../components/UI/Background';

const MainPage = () => {
  // 더미데이터
  const nags = [
    {
      nagId: 1,
      content: '니 코드가 개발새발인데 놀고싶어?',
      createAt: null,
    },
    {
      nagId: 2,
      content: '일어나',
      createAt: null,
    },
    {
      nagId: 3,
      content: '답없누',
      createAt: null,
    },
    {
      nagId: 4,
      content: '잠이나 자',
      createAt: null,
    },
    {
      nagId: 5,
      content: '답답하다~',
      createAt: null,
    },
  ];

  // 문장을 뿌려줄 좌표를 저장하는 상태
  const [positions, setPositions] = useState([]);

  // 컴포넌트가 마운트될 때 랜덤한 좌표를 생성하여 상태에 저장
  useEffect(() => {
    const generateRandomPosition = () => {
      const maxX = window.innerWidth - 30; // 최대 x좌표
      const maxY = window.innerHeight - 30; // 최대 y좌표
      const RandomX = Math.floor(Math.random() * maxX);
      const RandomY = Math.floor(Math.random() * maxY);
      console.log(RandomX, RandomY);
      return { x: RandomX, y: RandomY };
    };

    const newPositions = nags.map(() => generateRandomPosition());
    setPositions(newPositions);
  }, []);

  return (
    <Background>
      <StartButton nagCount={'172032'} />
      {nags.map((nag, index) => (
        <Wrap>
          <ContentContainer
            key={index}
            style={{
              position: 'absolute',
              top: positions[index]?.y || 100,
              left: positions[index]?.x || 100,
            }}
          >
            {nag.content}
          </ContentContainer>
        </Wrap>
      ))}
    </Background>
  );
};

export default MainPage;
const motionAnimation = keyframes`
  0% {
    margin-top: 0px;
  }
  100% {
    margin-top: 10px;
  }
`;
const Wrap = styled.div`
  text-align: center;
  margin-top: 20px;
`;

const ContentContainer = styled.div`
  animation: ${motionAnimation} 2s linear 0s infinite alternate;
  margin-top: 0;
  -webkit-animation: ${motionAnimation} 2s linear 0s infinite alternate;
  margin-top: 0;
  color: white;
  font-weight: bold;
  z-index: 0;
`;
