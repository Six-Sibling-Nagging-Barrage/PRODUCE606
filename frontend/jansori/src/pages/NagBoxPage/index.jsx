import React from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import tw, { styled } from 'twin.macro';

const NagBoxPage = () => {
  const NagSentences = [
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
          {NagSentences.map((nagSentence, index) => (
            <CountBox
              key={index}
              startSentence={nagSentence.startSentence}
              count={counts[index] + nagSentence.expression}
              endSentence={nagSentence.endSentence}
            />
          ))}
        </CountBoxContainer>
      </CenteredContainer>
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
