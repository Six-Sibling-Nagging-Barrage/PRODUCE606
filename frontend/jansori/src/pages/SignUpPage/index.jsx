import React from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import ProfileImg from '../../components/Profile/ProfileImg';
import HashTag from '../../components/HashTag/HashTag';
import Button from '../../components/UI/Button';

const SignUpPage = () => {
  const {
    register,
    formState: { errors },
    handleSubmit,
    setError,
  } = useForm({ mode: 'onBlur' });

  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle>회원가입</SignUpTitle>
        <SignUpForm>
          <ProfileImg editable={true} />
          <Nickname>
            <Label>닉네임</Label>
            <input
              {...register('nickname', {
                required: '닉네임을 입력해주세요.',
                maxLength: {
                  value: 11,
                  message: '11자 이하로 입력해주세요.',
                },
                pattern: {
                  value:
                    /^(?!.*\s\s)[\u1100-\u1112\u3131-\u314e\u314f-\u3163가-힣\w _]+(?<![\s_])$/,
                  message: '닉네임 형식을 확인해주세요.',
                },
              })}
            />
            <ErrorMessage>{errors?.nickname?.message}</ErrorMessage>
          </Nickname>
          <Bio>
            <Label>소개글</Label>
            <textarea
              {...register('description', {
                maxLength: {
                  value: 200,
                  message: '200자 이하로 입력해주세요.',
                },
                pattern: {
                  value:
                    /^(?!.*\s\s)[[\u1100-\u1112\u3131-\u314e\u314f-\u3163가-힣]+$/,
                  message: '공백 문자를 연속 2개 이상 사용할 수 없어요.',
                },
              })}
            />
            <ErrorMessage>{errors?.description?.message}</ErrorMessage>
          </Bio>
        </SignUpForm>
        <HashTag />
        <Button normal label={'완료'} />
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
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.5);
  padding: 16px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
`;

const SignUpForm = styled.form``;

const SignUpTitle = styled.div`
  margin: 20px;
`;

const Label = styled.div`
  margin-bottom: 5px;
`;

const Nickname = styled.div`
  height: 100px;
  & > input {
    padding: 5px;
    border-radius: 5px;
  }
`;

const ErrorMessage = styled.div`
  margin-top: 5px;
  font-size: 14px;
  color: gray;
`;

const Bio = styled.div`
  width: 100%;
  height: 170px;
  & > textarea {
    padding: 5px;
    border-radius: 5px;
    width: 100%;
    height: 100px;
  }
`;

export default SignUpPage;
