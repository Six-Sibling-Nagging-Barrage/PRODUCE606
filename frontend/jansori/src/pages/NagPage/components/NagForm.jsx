import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import { useForm } from 'react-hook-form';
import HashTag from '../../../components/HashTag/HashTag';
import Button from '../../../components/UI/Button';
import '@animxyz/core';
import { XyzTransition } from '@animxyz/react';

const NagForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({ mode: 'onBlur' });

  const [buttonToggled, setButtonToggled] = useState(true);

  // 버튼을 클릭하여 사라지도록 처리하는 핸들러 함수
  const onSubmit = (data) => {
    setButtonToggled(false);
    console.log(data);
    reset();
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      reset();
      setButtonToggled(true);
    }, 1000);

    return () => clearTimeout(timer);
  }, [buttonToggled]);

  return (
    <div>
      {buttonToggled && (
        <XyzTransition appear duration='auto' xyz='fade up-100% duration-10'>
          <NagFormWrap xyz='fade up-100%'>
            <NagFormTitle>잔소리 보내기</NagFormTitle>
            <NagFormContainer onSubmit={handleSubmit(onSubmit)}>
              <NagContent>
                <textarea
                  defaultValue=''
                  {...register('description', {
                    minLength: {
                      value: 2,
                      message: '잔소리는 2자 이상으로 입력해주세요.',
                    },
                    maxLength: {
                      value: 150,
                      message: '잔소리는 150자 이하로 입력해주세요.',
                    },
                    pattern: {
                      value:
                        /^(?!.*\s\s)(?!.*\s$)[[\u1100-\u1112\u3131-\u314e\u314f-\u3163가-힣\s]+$/,
                      message: '공백 문자를 연속 2개 이상 사용할 수 없어요.',
                    },
                  })}
                />
                <ErrorMessage>{errors?.description?.message}</ErrorMessage>
              </NagContent>
              <HashTag hashTagLimit={1} />
              <Button normal='true' label={'보내기'} />
            </NagFormContainer>
          </NagFormWrap>
        </XyzTransition>
      )}
    </div>
  );
};

export default NagForm;

const NagFormTitle = styled.div`
  ${tw`text-center font-bold m-6 text-base
  lg:text-2xl
  md:text-2xl
  sm:text-2xl`}
`;

const NagFormContainer = styled.form`
  ${tw`w-full max-w-md mx-auto h-full`}
`;

const NagContent = styled.div`
  width: 100%;
  height: 1100%;
  & > textarea {
    padding: 5px;
    border-radius: 5px;
    width: 100%;
    height: 100px;
  }
`;

const ErrorMessage = styled.div`
  margin: 5px 0;
  font-size: 14px;
  color: gray;
`;

const NagFormWrap = styled.div`
  position: absolute;
  top: 55%;
  left: 50%;
  width: 40%;
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
