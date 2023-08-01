import React from 'react';
import Card from '../../components/UI/Card';
import tw, { styled } from 'twin.macro';
import profileImg from '../../assets/profileImg.png';

const CharacterInfoPage = () => {
  const characterInfos = [
    {
      name: '오구',
      desc: '긴 캐릭터 설명1',
      img: profileImg,
    },
    {
      name: '오구',
      desc: '긴 캐릭터 설명2',
      img: profileImg,
    },
    {
      name: '오구',
      desc: '긴 캐릭터 설명3',
      img: profileImg,
    },
    {
      name: '오구',
      desc: '긴 캐릭터 설명4',
      img: profileImg,
    },
    {
      name: '오구',
      desc: '긴 캐릭터 설명5',
      img: profileImg,
    },
    {
      name: '오구',
      desc: '긴 캐릭터 설명6',
      img: profileImg,
    },
  ];

  return (
    <PageContainer>
      <CharacterInfoContainer>
        {characterInfos.map((characterInfo, index) => {
          return (
            <Card
              key={index}
              img={characterInfo.img}
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
  ${tw`
  flex 
  justify-center 
  items-center 
  min-h-screen 
  sm:mt-32 
  md:mt-32 
  lg:mt-4`}
`;

const CharacterInfoContainer = styled.div`
  ${tw`grid 
  gap-x-16 
  gap-y-8 
  w-fit
  sm:grid-cols-1
  md:grid-cols-2
  lg:grid-cols-3 `}
`;
