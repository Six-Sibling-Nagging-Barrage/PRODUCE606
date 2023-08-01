import React, { useEffect, useState } from 'react';
import { styled } from 'twin.macro';
import profileImg from '../../../assets/profileImg.png';

const personaInfo = [
  {
    id: 1,
    name: '캐릭터1',
    img: profileImg,
    bio: '캐릭터1의 설명입니다',
  },
  {
    id: 2,
    name: '캐릭터2',
    img: profileImg,
    bio: '캐릭터2의 설명입니다',
  },
  {
    id: 3,
    name: '캐릭터3',
    img: profileImg,
    bio: '캐릭터3의 설명입니다',
  },
  {
    id: 4,
    name: '캐릭터4',
    img: profileImg,
    bio: '캐릭터4의 설명입니다',
  },
  {
    id: 5,
    name: '캐릭터5',
    img: profileImg,
    bio: '캐릭터5의 설명입니다',
  },
  {
    id: 6,
    name: '캐릭터6',
    img: profileImg,
    bio: '캐릭터6의 설명입니다',
  },
];

const PersonaReaction = (props) => {
  const { personas, todoId, setShowMore, setPersonaNagList } = props;

  const [personaIndex, setPersonaIndex] = useState(-1);
  const [isAdded, setIsAdded] = useState(
    Array.from({ length: 6 }, () => false)
  ); // 이미 반응한 캐릭터인지 저장하는 배열
  const [personaLikeCount, setPersonaLikeCount] = useState([]);

  useEffect(() => {
    personas.map((persona) => {
      setPersonaLikeCount((prev) => [...prev, persona.likeCount]);
    });
  }, []);

  const handlePersonaHover = (personaId) => {
    setPersonaIndex(personaId - 1);
  };

  const handleClickPersonaReaction = (personaId) => {
    if (isAdded[personaId - 1]) return;
    // 캐릭터 반응 api 호출
    // axios.post(`http://localhost:8080//todo/${todoId}/${personaId}`, config).then(res => ...);
    const personaNag = {
      todoPersonaId: 64,
      personaId: personaId,
      likeCount: 1,
      content: '새로 추가된 캐릭터 잔소리',
      isFirstReaction: true,
    };

    if (!personaNag.isFirstReaction) return;

    setPersonaLikeCount((prev) =>
      prev.map((item, index) => {
        if (index === personaId - 1) {
          return item + 1;
        }
        return item;
      })
    );
    setIsAdded((prev) =>
      prev.map((item, index) => {
        if (index === personaId - 1) {
          return true;
        }
        return item;
      })
    );
    setPersonaNagList((prev) => [...prev, personaNag]);
    setShowMore(true);
  };

  return (
    <>
      <PersonaContainer>
        {personas.map((persona) => {
          return (
            <PersonaProfile
              key={persona.personaId}
              onMouseEnter={() => {
                handlePersonaHover(persona.personaId);
              }}
              onClick={() => {
                handleClickPersonaReaction(persona.personaId);
              }}
            >
              <img
                className="w-10 h-10 rounded-full"
                src={profileImg} // TODO: 캐릭터 이미지
                alt="Rounded avatar"
              />
              <CountBadge>
                {personaLikeCount[persona.personaId - 1] < 100
                  ? personaLikeCount[persona.personaId - 1]
                  : '99+'}
              </CountBadge>
            </PersonaProfile>
          );
        })}
      </PersonaContainer>
      {personaIndex === -1 ? (
        <PersonaBio>캐릭터를 클릭해 잔소리를 해주세요.</PersonaBio>
      ) : (
        <PersonaBio>
          <div>{personaInfo[personaIndex].name}</div>
          <div>{personaInfo[personaIndex].bio}</div>
        </PersonaBio>
      )}
    </>
  );
};

const PersonaContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
  margin-top: 10px;
`;

const PersonaProfile = styled.div`
  height: 40px;
  &:hover {
    cursor: pointer;
  }
  position: relative;
`;

const CountBadge = styled.div`
  position: absolute;
  top: -10px;
  left: 30px;
  background-color: rgb(255, 121, 121);
  padding: 0 5px;
  border-radius: 15px;
  font-size: 13px;
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
