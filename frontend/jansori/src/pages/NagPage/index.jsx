import React, { useEffect } from 'react';
import NagForm from './components/NagForm';
import FeedBackground from '../../components/UI/FeedBackground';
import { styled } from 'twin.macro';
import { leftPersonas, rightPersonas } from '../../constants/persona';

const NagPage = () => {
  return (
    <NagPageContainer>
      <FeedBackground />
      <Left>
        {leftPersonas.map((persona) => {
          return (
            <PersonaContainer>
              <img src={persona.imgUrl} />
            </PersonaContainer>
          );
        })}
      </Left>
      <NagForm />
      <Right>
        {rightPersonas.map((persona) => {
          return (
            <PersonaContainer>
              <img src={persona.imgUrl} />
            </PersonaContainer>
          );
        })}
      </Right>
    </NagPageContainer>
  );
};

const NagPageContainer = styled.div`
  display: flex;
  justify-content: space-between;
  padding-top: 72px;
`;

const Left = styled.div`
  display: grid;
  grid-template-rows: repeat(3, 1fr);
  height: calc(100vh - 72px);
  gap: 2px;
  width: 30%;
  position: relative;
  & div:nth-child(odd) {
    & > img {
      position: absolute;
      left: 60px;
    }
  }
  & div:nth-child(even) {
    & > img {
      position: absolute;
      right: 60px;
    }
  }
`;

const Right = styled.div`
  display: grid;
  grid-template-rows: repeat(3, 1fr);
  height: calc(100vh - 72px);
  gap: 2px;
  width: 30%;
  position: relative;
  & div:nth-child(odd) {
    & > img {
      position: absolute;
      left: 60px;
    }
  }
  & div:nth-child(even) {
    & > img {
      position: absolute;
      right: 60px;
    }
  }
`;

const PersonaContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  & > img {
    height: 120px;
  }
`;

export default NagPage;
