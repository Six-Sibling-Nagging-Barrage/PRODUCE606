import React from 'react';
import Background from '../../components/UI/Background';
import CountBox from './components/CountBox';
import tw, { styled } from 'twin.macro';

const NagBoxPage = () => {
  return (
    <Background>
      <CenteredContainer>
        <CountBoxContainer>
          <CountBox
            startSentence={'현재 총'}
            count={'127' + '개'}
            endSentence={'잔소리가 작성되었습니다.'}
          />
          <CountBox
            startSentence={'현재 총'}
            count={'127' + '개'}
            endSentence={'잔소리가 작성되었습니다.'}
          />
          <CountBox
            startSentence={'현재 총'}
            count={'127' + '개'}
            endSentence={'잔소리가 작성되었습니다.'}
          />
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
