import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import { personas } from '../../constants/persona';
import logoImg from '../../assets/jansori-logo-eating-removebg-preview.png';
import { useImageErrorHandler } from '../../hooks/useImageErrorHandler';
import FeedBackground from '../../components/UI/FeedBackground';

const CharacterInfoPage = () => {
  const [personaIndex, setPersonaIndex] = useState(-1);

  const handleImgError = useImageErrorHandler();

  const handlePersonaHover = (personaId) => {
    setPersonaIndex(personaId - 1);
  };

  return (
    <>
      {/* <FeedBackground /> */}
      <PageContainer>
        {/* 페르소나 이미지 gif 확대 해서 보여지는 부분 */}
        <PersonaGifContainer>
          {personaIndex === -1 ? (
            <LogoImg src={logoImg} />
          ) : (
            <PersonaBigProfile key={personas[personaIndex].id}>
              <PersonaBigImg src={personas[personaIndex].gifUrl} onError={handleImgError} />
            </PersonaBigProfile>
          )}
        </PersonaGifContainer>
        {/* 페르소나 이미지 작게 출력되는 부분 */}
        <PersonaSmallImgContainer personaId={personaIndex}>
          {personas.map((persona) => {
            return (
              <PersonaProfile
                personaId={personaIndex}
                key={persona.id}
                onMouseEnter={() => handlePersonaHover(persona.id)}
              >
                <MiniProfileBackground isSelected={personaIndex === persona.id - 1} />
                <PersonaImg src={persona.imgUrl} onError={handleImgError} />
              </PersonaProfile>
            );
          })}
        </PersonaSmallImgContainer>
        {/* 페르소나 설명 출력 */}
        <PersonaDescriptionContainer>
          {personaIndex === -1 ? (
            <PersonaFirst>
              캐릭터에 마우스를 올리면 캐릭터에 대한 설명을 보실 수 있어요..
            </PersonaFirst>
          ) : (
            <PersonaBio>
              <PersonaDetailName>{personas[personaIndex].name}</PersonaDetailName>
              <PersonaDetailDesc>
                <PersonaDetailDescBio>
                  <PersonaDetailDescTitle>🧐 성격</PersonaDetailDescTitle>
                  <PersonaDetailDescContent>{personas[personaIndex].bio}</PersonaDetailDescContent>
                </PersonaDetailDescBio>
                <PersonaDetailDescBehind>
                  <PersonaDetailDescTitle>🙄 비하인드 스토리</PersonaDetailDescTitle>
                  <PersonaDetailDescContent>
                    {personas[personaIndex].behindStory}
                  </PersonaDetailDescContent>
                </PersonaDetailDescBehind>
              </PersonaDetailDesc>
            </PersonaBio>
          )}
        </PersonaDescriptionContainer>
      </PageContainer>
    </>
  );
};

export default CharacterInfoPage;

const PageContainer = styled.div`
  margin-top: 20px;
  align-items: center;
`;

const LogoImg = styled.img`
  height: 20vh;
  margin-top: 10vh;
  margin-bottom: 10vh;
`;

const PersonaGifContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 40vh;
`;

const PersonaBigImg = styled.img`
  height: 40vh;
`;

// 페르소나 이미지 작게 출력되는 부분
const PersonaSmallImgContainer = styled.div`
  position: relative;
  z-index: 1;
  width: 40vw;
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  margin: 0 auto;
  justify-content: center;
  @media (max-width: 768px) {
    width: 70vw;
    grid-template-columns: repeat(6, 1fr);
  }
`;

const PersonaBigProfile = styled.div`
  display: flex;
`;

const PersonaProfile = styled.div`
  display: flex;
  position: relative;
`;

const MiniProfileBackground = styled.div`
  position: absolute;
  z-index: 0;
  width: 100%;
  height: 100%;
  background-color: ${({ isSelected }) => (isSelected ? '#eeeef5' : 'transparent')};
  border-radius: ${({ isSelected }) => (isSelected ? '50%' : 'transparent')};
  padding: 3vh;
  top: 0;
  left: 0;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const PersonaImg = styled.img`
  ${tw`w-20`}
  z-index: 2;
  margin: 0 auto;
`;

// 페르소나 설명 출력하는 부분
const PersonaDescriptionContainer = styled.div`
  position: relative;
  top: -15px;
  width: 60vw;
  height: 28vh;
  margin: 0 auto;
  padding-top: 3vh;
  background-color: #eeeef5;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
  overflow-y: scroll;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
  @media (max-width: 768px) {
    width: 80vw;
    grid-template-columns: repeat(6, 1fr);
  }
`;

const PersonaFirst = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 90%;
`;

const PersonaBio = styled.div``;

const PersonaDetailName = styled.div`
  z-index: 1;
  font-weight: bold;
  font-size: 28px;
`;

const PersonaDetailDesc = styled.div`
  z-index: 1;
  width: 90%;
  margin: 0 auto;
  margin-top: 2vh;
  text-align: left;
`;

const PersonaDetailDescBio = styled.div`
  margin-bottom: 2vh;
`;

const PersonaDetailDescBehind = styled.div`
  margin-bottom: 2vh;
`;

const PersonaDetailDescTitle = styled.div`
  font-weight: 500;
  font-size: 18px;
  margin-bottom: 1vh;
`;

const PersonaDetailDescContent = styled.div`
  width : 90%';
  margin: 0 auto;
`;
