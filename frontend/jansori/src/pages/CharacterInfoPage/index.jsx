import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import { personas } from '../../constants/persona';
import logoImg from '../../assets/jansori-logo-eating-removebg-preview.png';
import { useImageErrorHandler } from '../../hooks/useImageErrorHandler';

const CharacterInfoPage = () => {
  const [personaIndex, setPersonaIndex] = useState(-1);

  const handleImgError = useImageErrorHandler();

  const handlePersonaHover = (personaId) => {
    setPersonaIndex(personaId - 1);
  };

  return (
    <PageContainer>
      {/* 페르소나 이미지 gif 확대 해서 보여지는 부분 */}
      <PersonaGifContainer>
        {personaIndex === -1 ? (
          <LogoImg src={logoImg} />
        ) : (
          <PersonaProfile key={personas[personaIndex].id}>
            <PersonaBigImg
              src={personas[personaIndex].gifUrl}
              onError={handleImgError}
            />
          </PersonaProfile>
        )}
      </PersonaGifContainer>
      {/* 페르소나 이미지 작게 출력되는 부분 */}
      <PersonaSmallImgContainer>
        {personas.map((persona) => {
          return (
            <PersonaProfile
              key={persona.id}
              onMouseEnter={() => handlePersonaHover(persona.id)}
            >
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
                <div>{personas[personaIndex].bio}</div>
              </PersonaDetailDescBio>
              <PersonaDetailDescBehind>
                <PersonaDetailDescTitle>
                  🫢 비하인드 스토리
                </PersonaDetailDescTitle>
                <div>{personas[personaIndex].behindStory}</div>
              </PersonaDetailDescBehind>
            </PersonaDetailDesc>
          </PersonaBio>
        )}
      </PersonaDescriptionContainer>
    </PageContainer>
  );
};

export default CharacterInfoPage;

const PageContainer = styled.div`
  margin-top: 15vh;
  align-items: center;
`;

const LogoImg = styled.img`
  height: 20vh;
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

const PersonaProfile = styled.div`
  display: flex;
`;

const PersonaImg = styled.img`
  ${tw`w-20`}
`;

// 페르소나 설명 출력하는 부분
const PersonaDescriptionContainer = styled.div`
  width: 80vw;
  height: 30vh;
  margin: 0 auto;
  margin-top: 3vh;
  padding-top: 3vh;
  border: 2px solid #d8d8d8;
  background-color: rgb(244, 244, 244);
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
  overflow-y: scroll;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

const PersonaFirst = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 90%;
`;

const PersonaBio = styled.div``;

const PersonaDetailName = styled.div`
  font-weight: bold;
  font-size: 28px;
`;

const PersonaDetailDesc = styled.div`
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
`;
