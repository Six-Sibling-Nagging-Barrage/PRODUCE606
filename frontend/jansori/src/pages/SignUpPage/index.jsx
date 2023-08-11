import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import Button from '../../components/UI/Button';
import { createSignUp } from '../../apis/api/member';
import { useNavigate } from 'react-router-dom';
import { memberIdState } from '../../states/user';
import { useSetRecoilState } from 'recoil';
import { validateEmail, validatePassword } from '../../utils/validate';

const SignUpPage = () => {
  const navigate = useNavigate();
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    watch,
  } = useForm({ mode: 'onBlur' });

  const setMemberId = useSetRecoilState(memberIdState);

  const [signupError, setSignupError] = useState(false);

  const signUpSubmit = async (data) => {
    const user = {
      email: data.email,
      password: data.password,
    };
    // TODO: 회원가입 api 호출
    const res = await createSignUp(user);
    if (res.code === '200') {
      setSignupError(false);
      // setMemberId(res.data.memberId);
      // navigate('/initialprofile');
      navigate('/login');
    } else if (res.data.code === '853') {
      setSignupError(true);
    }
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
            <Input
              placeholder="이메일을 입력해주세요."
              {...register('email', {
                required: '이메일을 입력해주세요.',
                validate: validateEmail,
              })}
              onKeyDown={handleFormKeyDown}
            />
            {errors.email && (
              <ErrorMessage>{errors.email?.message}</ErrorMessage>
            )}
          </InfoContainer>
          <InfoContainer>
            <Label>비밀번호</Label>
            <Input
              type="password"
              placeholder="비밀번호를 입력해주세요."
              {...register('password', {
                required: '비밀번호를 입력해주세요.',
                validate: validatePassword,
              })}
              onKeyDown={handleKeyDownSubmit}
            />
            {errors.password && (
              <ErrorMessage>{errors.password.message}</ErrorMessage>
            )}
          </InfoContainer>
          <InfoContainer>
            <Label>비밀번호 확인</Label>
            <Input
              type="password"
              placeholder="비밀번호를 한 번 더 입력해주세요."
              {...register('confirmPassword', {
                required: '비밀번호를 한 번 더 입력해주세요.',
                validate: (value) =>
                  value === watch('password') ||
                  '비밀번호가 일치하지 않습니다.',
              })}
              onKeyDown={handleKeyDownSubmit}
            />
            {!errors.confirmPassword &&
              watch('password') &&
              watch('confirmPassword') === watch('password') && (
                <MatchingMessage>비밀번호가 일치합니다!</MatchingMessage>
              )}
          </InfoContainer>
          {signupError && (
            <ErrorMessage>이미 가입된 이메일이네요!</ErrorMessage>
          )}
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
  top: 45%;
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
  background-color: rgba(255, 255, 255, 0.6);
  padding: 10px 60px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 30px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
`;

const SignUpTitle = styled.div`
  margin: 20px;
  font-weight: bold;
  font-size: 20px;
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

const Input = styled.input`
  width: 100%;
  height: 50px;
  padding: 5px;
  border-radius: 30px;
  text-align: center;
  &:focus {
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }
`;

const ErrorMessage = styled.div`
  font-size: 14px;
  color: #ff6c6c;
  margin-top: 5px;
`;

const MatchingMessage = styled.div`
  font-size: 14px;
  color: #66bb6a;
  margin-top: 5px;
`;

const Footer = styled.div`
  margin: 10px 0 20px;
`;

export default SignUpPage;
