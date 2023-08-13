import React, { useState, useEffect, useRef } from 'react';
import styled, { keyframes } from 'styled-components';
import StartButton from './components/StartButton';
import Background from '../../components/UI/Background';
import { getMainNags, getNagBoxStatistics } from '../../apis/api/nag';

const MainPage = () => {
  // 문장을 뿌려줄 좌표를 저장하는 상태
  const [positions, setPositions] = useState([]);
  const [randomNags, setRandomNags] = useState([]);
  const [totalNagCount, setTotalNagCount] = useState('');

  // startButton의 ref
  const startButtonWrapRef = useRef(null);

  useEffect(() => {
    (async () => {
      const data = await getMainNags();
      const dataStatic = await getNagBoxStatistics();
      setRandomNags(data?.nags);
      setTotalNagCount(dataStatic.totalNagsCount);
    })();
  }, []);

  // 랜덤 잔소리를 가져오면 랜덤한 좌표를 생성하여 상태에 저장
  useEffect(() => {
    if (!randomNags) return;
    const startButtonWrapRect = startButtonWrapRef.current.getBoundingClientRect();

    // 사분면에 랜덤으로 배치
    const getQuadrantPosition = (index) => {
      const quadrant = (index % 4) + 1;
      const maxX = window.innerWidth - 300;
      const maxY = window.innerHeight - 50;

      const xCordination = Math.floor((Math.random() * maxX) / 2);
      const yCordination = Math.floor((Math.random() * maxY) / 2);

      // 시작 버튼 y좌표값 위 아래
      const startButtonBottom = startButtonWrapRect.bottom;
      const startButtonTop = startButtonWrapRect.top - startButtonWrapRect.height;

      // 중복 체크하는 함수
      const checkPosition = (y) => {
        if (startButtonTop <= y && y <= startButtonBottom) {
          return false; //startButton 높이에 들어가는 경우
        }
        return true;
      };

      let newX = 0;
      let newY = 0;

      switch (quadrant) {
        case 1: // 1사분면\
          newX = xCordination + maxX / 2;
          newY = yCordination + 80;
          break;
        case 2: // 2사분면
          newX = xCordination;
          newY = yCordination + 80;
          break;
        case 3: // 3사분면
          newX = xCordination;
          newY = yCordination + maxY / 2;
          break;
        case 4: // 4사분면
          newX = xCordination + maxX / 2;
          newY = yCordination + maxY / 2;
          break;
        default:
          newX = 100;
          newY = 100;
          break;
      }
      while (!checkPosition(newY)) {
        if (quadrant === 1 || quadrant === 2) {
          newY -= 5;
        } else {
          newY += 5;
        }
      }

      return { x: newX, y: newY };
    };

    const newPositions = randomNags?.map((nag, index) => {
      return getQuadrantPosition(index);
      // return index === nags.length - 1 ? generateRandomPosition() : getQuadrantPosition(index);
    });

    setPositions(newPositions);
  }, [randomNags]);

  return (
    <Background>
      <StartButtonWrap ref={startButtonWrapRef}>
        <StartButton nagCount={totalNagCount} />
      </StartButtonWrap>
      <NagsContainer>
        {randomNags &&
          randomNags.map((nag, index) => (
            <Wrap
              key={nag.nagId}
              style={{
                position: 'absolute',
                top: positions[index]?.y,
                left: positions[index]?.x,
              }}
            >
              <ContentContainer key={index}>{nag.content}</ContentContainer>
            </Wrap>
          ))}
      </NagsContainer>
    </Background>
  );
};

export default MainPage;

const StartButtonWrap = styled.div`
  z-index: 30;
  width: 100%;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

const motionAnimation = keyframes`
  0% {
    margin-top: 0px;
  }
  100% {
    margin-top: 15px;
  }
`;
const Wrap = styled.div`
  text-align: center;
  margin-top: 20px;
`;

const ContentContainer = styled.div`
  width: 300px;
  animation: ${motionAnimation} 1s linear 0s infinite alternate;
  margin-top: 0;
  -webkit-animation: ${motionAnimation} 1s linear 0s infinite alternate;
  margin-top: 0;
  color: #efefef;
  font-weight: 600;
`;

const NagsContainer = styled.div`
  z-index: 0;
`;
