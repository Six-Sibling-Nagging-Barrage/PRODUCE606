import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import Button from '../../components/UI/Button';
import { createSignUp } from '../../apis/api/member';
import { useNavigate } from 'react-router-dom';

const SignUpPage = () => {
  const navigate = useNavigate();
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
  } = useForm({ mode: 'onBlur' });

  const signUpSubmit = async (data) => {
    const user = {
      email: data.email,
      password: data.password,
    };
    // TODO: 회원가입 api 호출
    const res = await createSignUp(user);
    navigate('/login');
  };

  const handleFormKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
    }
  };

  const handleKeyDownSubmit = (e) => {
    if (e.key === 'Enter') {
      handleSubmit(signUpSubmit);
    }
  };

  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle>회원가입</SignUpTitle>
        <form>
          <InfoContainer>
            <Label>이메일</Label>
            <Email
              placeholder="이메일을 입력해주세요."
              {...register('email', {
                required: '이메일을 입력해주세요.',
              })}
              onKeyDown={handleFormKeyDown}
            />
          </InfoContainer>
          <InfoContainer>
            <Label>비밀번호</Label>
            <Password
              type="password"
              placeholder="비밀번호를 입력해주세요."
              {...register('password', {
                required: '비밀번호를 입력해주세요.',
              })}
              onKeyDown={handleKeyDownSubmit}
            />
          </InfoContainer>
          <Footer>
            <Button
              onClick={handleSubmit(signUpSubmit)}
              disabled={isSubmitting}
              normal
              label={'완료'}
            />
          </Footer>
        </form>
      </SignUpContainer>
    </Background>
  );
};

const SignUpContainer = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 30%;
  @media (min-width: 980px) and (max-width: 1200px) {
    width: 40%;
  }
  @media (min-width: 768px) and (max-width: 980px) {
    width: 50%;
  }
  @media (min-width: 600px) and (max-width: 768px) {
    width: 60%;
  }
  @media (max-width: 600px) {
    width: 70%;
  }
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.5);
  padding: 10px 40px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.2);
`;

const SignUpTitle = styled.div`
  margin: 10px;
  font-weight: bold;
`;

const Label = styled.div`
  font-size: 14px;
  color: gray;
  font-weight: bold;
  margin-bottom: 3px;
  text-align: left;
  padding-left: 5px;
`;

const InfoContainer = styled.div`
  margin-bottom: 15px;
`;

const Email = styled.input`
  width: 100%;
  padding: 5px;
  border-radius: 5px;
  text-align: center;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  }
`;

const Password = styled.input`
  width: 100%;
  padding: 5px;
  border-radius: 5px;
  text-align: center;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  }
`;

const Footer = styled.div`
  margin: 10px 0 20px;
`;

export default SignUpPage;
