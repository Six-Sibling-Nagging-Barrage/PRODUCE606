import React from "react";
import styled from "styled-components";
import tw from "twin.macro";

const Card = (props) => {
  const { name, desc } = props;
  return (
    <CardItem>
      <img
        className="mx-auto rounded-full h-48 w-48"
        src="https://i.namu.wiki/i/VxdEKDNZCp9hAW5TU5-3MZTePLGSdlYKzEZUyVLDB-Cyo950Ee19yaOL8ayxgJzEfMYfzfLcRYuwkmKEs2cg0w.webp"
        alt="character img"
      />
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
mb-5
mt-5
`}
`;

//캐릭터 설명
const CharacterDesc = styled.p`
  ${``}
`;
