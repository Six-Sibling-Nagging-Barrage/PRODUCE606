import React from "react";
import tw, { styled } from "twin.macro";

const Mark = (props) => {
  const { label } = props;
  return <MarkComponent>{label}</MarkComponent>;
};

export default Mark;

const MarkComponent = styled.div`
  ${tw`rounded-r-lg 
rounded-tl-3xl
w-fit
px-5
bg-white
shadow
shadow-gray-400
`}
`;
