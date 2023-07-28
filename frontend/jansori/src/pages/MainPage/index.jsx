import React from "react";
import styled from "styled-components";
import tw from "twin.macro";
import StartButton from "./components/StartButton";

const MainPage = () => {
  return (
    <BackgroundStyled>
      <StartButton nagCount={"172032"} />
    </BackgroundStyled>
  );
};

export default MainPage;

const BackgroundStyled = styled.div`
  ${tw`
  min-h-screen
  bg-gray-300
  snap-x
`}
`;
