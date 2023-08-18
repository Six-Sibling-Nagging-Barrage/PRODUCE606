import React from 'react';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import ProfileForm from '../../components/Profile/ProfileForm';

const InitialProfilePage = () => {
  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle></SignUpTitle>
        <ProfileForm initial={true} />
      </SignUpContainer>
    </Background>
  );
};

const SignUpContainer = styled.div`
  position: absolute;
  top: 52%;
  left: 50%;
  width: 50%;
  @media (min-width: 980px) and (max-width: 1200px) {
    width: 60%;
  }
  @media (min-width: 768px) and (max-width: 980px) {
    width: 70%;
  }
  @media (min-width: 500px) and (max-width: 768px) {
    width: 80%;
  }
  @media (max-width: 500px) {
    width: 90%;
  }
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.5);
  padding: 10px 50px;
  z-index: 30;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.2);
  overflow: auto;
  height: 85%;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

const SignUpTitle = styled.div`
  margin: 10px;
  color: gray;
  font-weight: bold;
`;

export default InitialProfilePage;
