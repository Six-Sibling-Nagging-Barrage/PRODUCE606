import React from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';

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
          <form>
            <div className='sign-up-input-item'>
              <label>닉네임</label>
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
              <div>{errors?.nickname?.message}</div>
            </div>
            <div className='sign-up-input-item'>
              <label>소개글</label>
              <input
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
              <div>{errors?.description?.message}</div>
            </div>
          </form>
        </SignUpForm>
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
