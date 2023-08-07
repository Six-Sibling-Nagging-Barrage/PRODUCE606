import React, { useEffect, useState } from 'react';
import { useSetRecoilState } from 'recoil';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import Button from '../../components/UI/Button';
import { createLogin } from '../../apis/api/member';
import { memberToken } from '../../states/user';

const LoginPage = () => {
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
  } = useForm({ mode: 'onBlur' });

  const setMemberToken = useSetRecoilState(memberToken);

  const loginSubmit = async (data) => {
    const user = {
      email: data.email,
      password: data.password,
    };
    // TODO: 로그인 api 호출
    const res = await createLogin(user);
    console.log(res.accessToken);
    setMemberToken(res.accessToken);
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle></SignUpTitle>
        <form onKeyDown={handleFormKeyDown}>
          <InfoContainer>
            <Label>이메일</Label>
            <Email
              placeholder="이메일을 입력해주세요."
              {...register('email', {
                required: '이메일을 입력해주세요.',
              })}
            />
          </InfoContainer>
          <InfoContainer>
            <Label>비밀번호</Label>
            <Password
              placeholder="비밀번호를 입력해주세요."
              {...register('password', {
                required: '비밀번호를 입력해주세요.',
              })}
            />
          </InfoContainer>
        </form>
        <Footer>
          <Button
            onClick={handleSubmit(loginSubmit)}
            disabled={isSubmitting}
            normal
            label={'완료'}
          />
        </Footer>
      </SignUpContainer>
    </Background>
  );
};

const SignUpContainer = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  width: 40%;
  @media (min-width: 980px) and (max-width: 1200px) {
    width: 50%;
  }
  @media (min-width: 768px) and (max-width: 980px) {
    width: 60%;
  }
  @media (min-width: 500px) and (max-width: 768px) {
    width: 80%;
  }
  @media (max-width: 500px) {
    width: 90%;
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
  color: gray;
  font-weight: bold;
`;

const Label = styled.div`
  font-size: 14px;
  color: gray;
  font-weight: bold;
  margin-bottom: 3px;
`;

const InfoContainer = styled.div`
  margin-bottom: 5px;
`;

const Email = styled.input`
  width: 300px;
  padding: 5px;
  border-radius: 5px;
  text-align: center;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  }
`;

const Password = styled.input`
  width: 300px;
  padding: 5px;
  border-radius: 5px;
  text-align: center;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  }
`;

const Bio = styled.textarea`
  padding: 10px;
  border-radius: 5px;
  width: 100%;
  height: 120px;
  margin: 0;
  font-size: 14px;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  }
`;

const Footer = styled.div`
  margin: 10px 0 20px;
`;

export default LoginPage;
