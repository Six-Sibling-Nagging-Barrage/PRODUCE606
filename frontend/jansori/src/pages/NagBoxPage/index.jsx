import React from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import { Link } from 'react-router-dom';
import tw, { styled } from 'twin.macro';

const NagBoxPage = () => {
  const nagSentences = [
    {
      startSentence: '현재 총',
      expression: ' 개',
      endSentence: '잔소리가 작성되었습니다.',
    },
    {
      startSentence: '지금까지',
      expression: ' 개',
      endSentence: 'todo가 달성했습니다.',
    },
    {
      startSentence: '현재 총',
      expression: ' 명',
      endSentence: '사용하고 있습니다.',
    },
  ];

  const counts = ['15698', '157', '586'];

  return (
    <Background>
      <CenteredContainer>
        <CountBoxContainer>
          {nagSentences.map((nagSentence, index) => (
            <CountBox
              key={index}
              startSentence={nagSentence.startSentence}
              count={counts[index] + nagSentence.expression}
              endSentence={nagSentence.endSentence}
            />
          ))}
        </CountBoxContainer>
      </CenteredContainer>

      {/* 잔소리 버튼 */}
      <SpeechBubble>
        <Link to='/nag'>
          <BubbleSpeech>
            <p>잔소리 하러 가기</p>
          </BubbleSpeech>
        </Link>
      </SpeechBubble>
    </Background>
  );
};

export default NagBoxPage;

const CenteredContainer = styled.div`
  ${tw`
    flex
    justify-center
    items-center
    h-full
  `}
`;

const CountBoxContainer = styled.div`
  ${tw`grid 
  grid-cols-3
  gap-4
  w-9/12
  w-fit
  py-20`}
`;

const SpeechBubble = styled.div`
  ${tw`
  w-2/3
  sm:w-1/3
  md:w-3/5
  lg:w-2/5
  mx-auto
`}
`;
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
