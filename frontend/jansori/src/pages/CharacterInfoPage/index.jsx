import React from 'react';
import Card from '../../components/UI/Card';
import tw, { styled } from 'twin.macro';
import ProfileImg from '../../components/Profile/ProfileImg';

const CharacterInfoPage = () => {
  const characterInfos = [
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명1',
    },
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명2',
    },
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명3',
    },
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명4',
    },
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명5',
    },
    {
      imgSrc: ProfileImg,
      name: '오구',
      desc: '긴 캐릭터 설명6',
    },
  ];

  return (
    <PageContainer>
      <CharacterInfoContainer>
        {characterInfos.map((characterInfo, index) => {
          return (
            <Card
              key={index}
              imgSrc={characterInfo.imgSrc}
              name={characterInfo.name}
              desc={characterInfo.desc}
            />
          );
        })}
      </CharacterInfoContainer>
    </PageContainer>
  );
};

export default CharacterInfoPage;

const PageContainer = styled.div`
  ${tw`flex justify-center items-center min-h-screen`}
`;

const CharacterInfoContainer = styled.div`
  ${tw`grid grid-cols-3 gap-x-16 gap-y-8 w-fit`}
`;
