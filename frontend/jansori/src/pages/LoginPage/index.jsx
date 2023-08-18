import React, { useState } from 'react';
import { useSetRecoilState, useRecoilState } from 'recoil';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import Button from '../../components/UI/Button';
import { createLogin } from '../../apis/api/member';
import {
  isLoginState,
  memberTokenState,
  memberRefreshTokenState,
  memberTokenExpState,
  memberInfoState,
  memberRoleState,
  memberIdState,
  ticketState,
  profileImgState,
  notificationState,
} from '../../states/user';
import { useNavigate } from 'react-router-dom';
import { validateEmail } from '../../utils/validate';
import { requestPermission } from '../../firebase';

const LoginPage = () => {
  const navigate = useNavigate();
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
  } = useForm({ mode: 'onBlur' });

  const [memberToken, setMemberToken] = useRecoilState(memberTokenState);
  const setMemberInfo = useSetRecoilState(memberInfoState);
  const setIsLogin = useSetRecoilState(isLoginState);
  const [memberRole, setMemberRole] = useRecoilState(memberRoleState);
  const setMemberId = useSetRecoilState(memberIdState);
  const setMemberRefreshToken = useSetRecoilState(memberRefreshTokenState);
  const setmemberTokenExp = useSetRecoilState(memberTokenExpState);
  const setTicket = useSetRecoilState(ticketState);
  const setProfileImg = useSetRecoilState(profileImgState);
  const setNotification = useSetRecoilState(notificationState);

  const [loginError, setLoginError] = useState(false);

  const loginSubmit = async (data) => {
    const user = {
      email: data.email,
      password: data.password,
      token: localStorage.getItem('fcmToken') ? localStorage.getItem('fcmToken') : undefined,
    };
    // 로그인 api 호출
    const res = await createLogin(user);
    if (res?.code === '200') {
      setLoginError(false);
      setMemberToken(res.data.accessToken);
      setMemberRefreshToken(res.data.refreshToken);
      setmemberTokenExp(res.data.accessTokenExpiresIn);
      setIsLogin(true);
      setMemberRole(res.data.memberRole);
      setMemberInfo({
        memberId: res.data.memberId,
        email: res.data.email,
        nickname: res.data.nickname,
        imageUrl: res.data.imageUrl,
      });
      setProfileImg(res.data.imageUrl);
      setMemberId(res.data.memberId);
      setTicket(res.data.ticket);
      setNotification(res.data.isUnreadNotificationLeft);
      // setFcmTokenState(process.env.REACT_APP_FIREBASE_VAPIDKEY);
      //recoil로 상태 fcm 부분 넣어두기
      if (res.data.memberRole === 'GUEST') {
        navigate('/initialprofile', { state: res.data.accessToken });
      } else navigate('/');
    } else {
      setLoginError(true);
    }
  };

  const handleFormKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
    }
  };

  const handleKeyDownSubmit = (e) => {
    if (e.key === 'Enter') {
      handleSubmit(loginSubmit);
    }
  };

  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle>로그인</SignUpTitle>
        <form>
          <InfoContainer>
            <Label>이메일</Label>
            <Input
              placeholder='이메일을 입력해주세요.'
              {...register('email', {
                required: '이메일을 입력해주세요.',
                validate: validateEmail,
              })}
              onKeyDown={handleFormKeyDown}
            />
            {errors.email && <ErrorMessage>{errors.email?.message}</ErrorMessage>}
          </InfoContainer>
          <InfoContainer>
            <Label>비밀번호</Label>
            <Input
              type='password'
              placeholder='비밀번호를 입력해주세요.'
              {...register('password', {
                required: '비밀번호를 입력해주세요.',
              })}
              onKeyDown={handleKeyDownSubmit}
            />
          </InfoContainer>
          {loginError && <ErrorMessage>아이디 또는 비밀번호를 확인하세요.</ErrorMessage>}
          <Footer>
            <Button onClick={handleSubmit(loginSubmit)} disabled={isSubmitting} normal>
              완료
            </Button>
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
  z-index: 30;
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

const Footer = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
`;

export default LoginPage;
