import React from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import tw, { styled } from 'twin.macro';

const NagBoxPage = () => {
  const startSentences = ['현재 총', '지금까지', '현재 총'];
  const counts = ['15698', '157', '586'];
  const expressions = [' 개', ' 개', ' 명'];
  const endSentences = ['잔소리가 작성되었습니다.', 'todo가 달성했습니다.', '사용하고 있습니다.'];

  return (
    <Background>
      <CenteredContainer>
        <CountBoxContainer>
          {startSentences.map((startSentence, index) => (
            <CountBox
              key={index}
              startSentence={startSentence}
              count={counts[index] + expressions[index]}
              endSentence={endSentences[index]}
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
