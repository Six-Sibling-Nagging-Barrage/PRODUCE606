import React, { useState } from 'react';
import { styled } from 'twin.macro';
import profileImg from '../../../assets/profileImg.png';

const PersonaReaction = () => {
  const [personaIndex, setPersonaIndex] = useState(-1);

  const personas = [
    {
      id: 0,
      name: '캐릭터1',
      img: profileImg,
      bio: '캐릭터1의 설명입니다',
    },
    {
      id: 1,
      name: '캐릭터2',
      img: profileImg,
      bio: '캐릭터2의 설명입니다',
    },
    {
      id: 2,
      name: '캐릭터3',
      img: profileImg,
      bio: '캐릭터3의 설명입니다',
    },
    {
      id: 3,
      name: '캐릭터4',
      img: profileImg,
      bio: '캐릭터4의 설명입니다',
    },
    {
      id: 4,
      name: '캐릭터5',
      img: profileImg,
      bio: '캐릭터5의 설명입니다',
    },
    {
      id: 5,
      name: '캐릭터6',
      img: profileImg,
      bio: '캐릭터6의 설명입니다',
    },
  ];

  const handlePersonaHover = (id) => {
    setPersonaIndex(id);
  };

  return (
    <>
      <PersonaContainer>
        {personas.map((persona) => {
          return (
            <PersonaProfile
              key={persona.id}
              onMouseEnter={() => {
                handlePersonaHover(persona.id);
              }}
            >
              <img
                class='w-10 h-10 rounded-full'
                src={persona.img}
                alt='Rounded avatar'
              />
            </PersonaProfile>
          );
        })}
      </PersonaContainer>
      {personaIndex === -1 ? (
        <PersonaBio>캐릭터를 클릭해 잔소리를 해주세요.</PersonaBio>
      ) : (
        <PersonaBio>
          <div>{personas[personaIndex].name}</div>
          <div>{personas[personaIndex].bio}</div>
        </PersonaBio>
      )}
    </>
  );
};

const PersonaContainer = styled.div`
  display: flex;
`;

const PersonaProfile = styled.div`
  height: 50px;
  width: 50px;
  &:hover {
    cursor: pointer;
  }
`;

const PersonaBio = styled.div`
  margin-top: 5px;
  position: relative;
  height: 80px;
  padding: 15px;
  background-color: rgb(238, 238, 238);
  border-radius: 10px;
  &:after {
    content: '';
    position: absolute;
    top: 0;
    left: 5%;
    width: 0;
    height: 0;
    border: 13px solid transparent;
    border-bottom-color: rgb(238, 238, 238);
    border-top: 0;
    border-right: 0;
    margin-left: -6.5px;
    margin-top: -12px;
  }
`;

export default PersonaReaction;
