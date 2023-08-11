import React, { useState, useEffect } from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import SpeechBubble from '../../components/UI/SpeechBubble';
import { Link } from 'react-router-dom';
import tw, { styled } from 'twin.macro';
import NagRankingList from './components/NagRankingList';
import { getNagBoxStatistics } from '../../apis/api/nag';

const NagBoxPage = () => {
  const [counts, setCounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const nagSentences = [
    {
      startSentence: '현재 총',
      expression: ' 개',
      endSentence: '잔소리가 쌓였어요.',
    },
    {
      startSentence: '지금까지',
      expression: ' 개',
      endSentence: 'TODO가 작성되었어요.',
    },
    {
      startSentence: '현재 총',
      expression: ' 명',
      endSentence: '이 사용 중이에요.',
    },
  ];

  useEffect(() => {
    (async () => {
      const data = await getNagBoxStatistics();
      setCounts(data);
      setLoading(false);
    })();
  }, []);

  return (
    <Background>
      <CenteredContainer>
        {loading ? (
          <LoadingMessage>Loading...</LoadingMessage>
        ) : (
          <CountBoxContainer>
            {nagSentences.map((nagSentence, index) => (
              <CountBox
                key={index}
                startSentence={nagSentence.startSentence}
                count={counts[Object.keys(counts)[index]] + nagSentence.expression}
                endSentence={nagSentence.endSentence}
              />
            ))}
          </CountBoxContainer>
        )}
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
  lg:w-2/5
  md:w-3/5
  sm:w-4/5
  mx-auto
  py-20
  `}
`;

const CountBoxContainer = styled.div`
  ${tw`grid 
  grid-cols-3
  gap-4`}
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
  ${tw`w-1/2
  mx-auto`}
`;

const NagTitle = styled.p`
  ${tw`font-bold text-2xl my-10`}
`;

const LoadingMessage = styled.p`
  ${tw`text-center text-gray-500 my-6 font-semibold text-4xl`}
`;
