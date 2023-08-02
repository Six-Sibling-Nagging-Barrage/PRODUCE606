import React from 'react';
import tw, { styled } from 'twin.macro';

const Card = (props) => {
  const { img, name, desc } = props;
  return (
    <CardItem>
      <img className='mx-auto rounded-full h-36 w-36 border-2' src={img} alt='character img' />
      <CharacterInfo>
        <CharacterName>{name}</CharacterName>
        <CharacterDesc>{desc}</CharacterDesc>
      </CharacterInfo>
    </CardItem>
  );
};

export default Card;

//카드
const CardItem = styled.div`
  ${tw`
  p-8
bg-white 
border
w-80
rounded-lg 
ease-in 
duration-300 
shrink-0`}
`;

//캐릭터 이름 + 설명
const CharacterInfo = styled.div`
  ${tw`flex flex-col items-center
w-full`}
`;

//캐릭터 이름
const CharacterName = styled.div`
  ${tw`
font-black
mb-3
mt-5
`}
`;

//캐릭터 설명
const CharacterDesc = styled.p`
  ${tw`
  text-slate-500
  `}
`;
