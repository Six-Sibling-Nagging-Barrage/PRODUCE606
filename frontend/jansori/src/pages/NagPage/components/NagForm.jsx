import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import { useForm } from 'react-hook-form';
import HashTag from '../../../components/HashTag/HashTag';
import Button from '../../../components/UI/Button';
import '@animxyz/core';
import { XyzTransition } from '@animxyz/react';
import { createNag } from '../../../apis/api/nag';

const validateNag = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '⚠ 연속된 공백 또는 앞뒤 공백은 사용할 수 없어요. ⚠';
  }
  return true;
};

const NagForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({ mode: 'onBlur' });
  const [hashTagList, setHashTagList] = useState([]);
  const [nagValue, setNagValue] = useState('');

  // 버튼을 클릭하여 사라지도록 처리하는 핸들러 함수
  const onSubmit = async (data) => {
    if (nagValue === '' || hashTagList.length === 0) {
      // TODO: 작은 경고 창 모달로 띄워주기!
      return;
    }

    const nag = {
      content: data.description,
      tagId: hashTagList[0].tagId,
    };
    const response = await createNag(nag);
    if (response.code === '200') {
      // TOO: 밑에 작은 알림으로 잔소리 전송에 성공하셨습니다. 알림 만들기
      console.log('success');
    } else {
      // TODO: 밑에 작은 알림으로 잔소리 전송에 실패하셨습니다. 알림 만들기
      console.log('fail');
    }
    // TODO: HASHTAG 초기화 하는 부분 추가
    reset();
    setHashTagList([]);
  };

  const handleNagInputChange = (event) => {
    setNagValue(event.target.value);
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {}, 500);

    return () => clearTimeout(timer);
  }, [nagValue]);

  return (
    <div>
      <XyzTransition appear duration='auto' xyz='fade up-100% duration-10'>
        <NagFormWrap xyz='fade up-100%'>
          <NagFormTitle>잔소리 보내기</NagFormTitle>
          <NagFormContainer>
            {errors?.description ? (
              <ErrorMessage>{errors?.description?.message}</ErrorMessage>
            ) : (
              <>
                {nagValue === '' ? (
                  <ErrorMessage>
                    📛 상처가 되는 말 말고 자극 받을 수 있는 말을 적어주세요! 📛
                  </ErrorMessage>
                ) : (
                  <ErrorMessage>💦 나쁜 말은 적지 않도록 항상 기억해주세요!! 💦</ErrorMessage>
                )}
              </>
            )}

            <NagContent>
              <textarea
                defaultValue=''
                {...register('description', {
                  minLength: {
                    value: 2,
                    message: '⚠ 잔소리는 2글자 이상으로 입력해주세요. ⚠',
                  },
                  maxLength: {
                    value: 150,
                    message: '⚠ 잔소리는 150글자 이하로 입력해주세요. ⚠',
                  },
                  validate: validateNag,
                })}
                onChange={handleNagInputChange}
                onKeyDown={handleFormKeyDown}
              />
            </NagContent>
            <HashTag
              editable={true}
              hashTagLimit={1}
              hashTagList={hashTagList}
              setHashTagList={setHashTagList}
            />
            <Button onClick={handleSubmit(onSubmit)} normal='true' label={'보내기'} />
          </NagFormContainer>
        </NagFormWrap>
      </XyzTransition>
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
  margin: 3vh 0;
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
