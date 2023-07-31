import React, { useState, useEffect } from 'react';
import StartButton from './components/StartButton';
import Background from '../../components/UI/Background';

const MainPage = () => {
  // 더미데이터
  const sentences = [
    '네 미해결 Todo list 모아서 논문도 쓰겠다.',
    '나는 자는 시간도 아까운데.. 넌 대체 뭐하고 있냐?',
    '와 드디어 시작하려고? 난 또 너가 죽은 줄 알았잖아 ㅋㅋ',
    '우와 방금 전에 먹고 또 먹는 거야? 신기해…',
    '나같으면 빨리 하겠다 ㅋㅋ',
    '노는게 그렇게 좋아?',
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

    const newPositions = sentences.map(() => generateRandomPosition());
    setPositions(newPositions);
  }, []);

  return (
    <Background>
      <StartButton nagCount={'172032'} />
      {sentences.map((sentence, index) => (
        <div
          key={index}
          style={{
            position: 'absolute',
            top: positions[index]?.y || 100,
            left: positions[index]?.x || 100,
          }}
        >
          {sentence}
        </div>
      ))}
    </Background>
  );
};

export default MainPage;
