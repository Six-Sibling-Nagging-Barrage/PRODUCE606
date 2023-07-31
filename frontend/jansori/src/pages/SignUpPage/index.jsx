import React from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';

const SignUpPage = () => {
  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle>회원가입</SignUpTitle>
        <SignUpForm></SignUpForm>
      </SignUpContainer>
    </Background>
  );
};

const SignUpContainer = styled.div`
  position: absolute;
  top: 55%;
  left: 50%;
  width: 30%;
  @media screen and (max-width: 768px) {
    width: 70%;
  }
  @media screen and (max-width: 1024px) {
    width: 50%;
  }
  height: 80vh;
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.5);
  padding: 16px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
`;

const SignUpTitle = styled.div`
  margin: 20px;
`;

const SignUpForm = styled.div`
  & .sign-up-input-item {
    height: 50px;
  }
`;

export default SignUpPage;
