import React from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import SpeechBubble from '../../components/UI/SpeechBubble';
import { Link } from 'react-router-dom';
import tw, { styled } from 'twin.macro';
import NagRankingList from './components/NagRankingList';

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
      <SpeechBubbleContainer>
        <Link to='/nag'>
          <SpeechBubble text='잔소리 하러 가기' normal />
        </Link>
      </SpeechBubbleContainer>

      {/* 잔소리 랭킹 */}
      <NagRannkingContainer>
        <NagTitle>현재 잔소리 TOP 5</NagTitle>
        <NagRankingList />
      </NagRannkingContainer>
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

const SpeechBubbleContainer = styled.div`
  ${tw`
  w-2/3
  sm:w-1/3
  md:w-3/5
  lg:w-2/5
  mx-auto
  mb-6
`}
`;

const NagRannkingContainer = styled.div`
  ${tw`w-2/3
  mx-auto`}
`;

const NagTitle = styled.p`
  ${tw`font-bold text-2xl my-10`}
`;
