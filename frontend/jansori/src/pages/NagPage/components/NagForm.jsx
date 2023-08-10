import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import { useForm } from 'react-hook-form';
import HashTag from '../../../components/HashTag/HashTag';
import Button from '../../../components/UI/Button';
import SnackBar from '../../../components/UI/SnackBar';
import '@animxyz/core';
import { XyzTransition } from '@animxyz/react';
import { createNag } from '../../../apis/api/nag';
import { useSetRecoilState } from 'recoil';
import { ticketState } from '../../../states/user';

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
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [checkSubmitted, setCheckSubmitted] = useState(false);

  const setTicket = useSetRecoilState(ticketState);

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  // 버튼을 클릭하여 사라지도록 처리하는 핸들러 함수
  const onSubmit = async (data) => {
    setCheckSubmitted(true);
    if (hashTagList.length === 0) {
      return;
    }

    const nag = {
      content: data.description,
      tagId: hashTagList[0].tagId,
    };
    const response = await createNag(nag);
    if (response.code === '200') {
      setTicket(response.data.ticketCount);
      // TODO: 잔소리 전송에 성공하셨습니다. 알림 만들기
      // TODO: 티켓 발급 알림
      console.log('success');
    } else {
      // TODO: 잔소리 전송에 실패하셨습니다. 알림 만들기
      console.log('fail');
    }
    setCheckSubmitted(false); //원래 상태로 복구
    setIsSubmitted(true);
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

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsSubmitted(false);
    }, 3000); // 3초 후에 애니메이션 클래스 제거
    return () => clearTimeout(timer);
  }, [isSubmitted]);

  return (
    <div>
      <XyzTransition appear duration="auto" xyz="fade up-100% duration-10">
        <NagFormWrap
          xyz={isSubmitted ? 'exit fade out-100% duration-100' : 'fade up-100%'}
        >
          <NagFormTitle>잔소리 보내기</NagFormTitle>
          <NagFormContainer>
            {errors?.description ? (
              <ErrorMessage>{errors?.description?.message}</ErrorMessage>
            ) : (
              <>
                {nagValue === '' ? (
                  <ErrorMessage>
                    📛 상처를 주는 말 말고 자극 받을 수 있는 말을 적어주세요! 📛
                  </ErrorMessage>
                ) : checkSubmitted && hashTagList.length === 0 ? (
                  <ErrorMessage>✒️ 해시태그를 입력해야 합니다 ✒️</ErrorMessage>
                ) : (
                  <ErrorMessage>
                    💦 나쁜 말은 적지 않도록 항상 기억해주세요!! 💦
                  </ErrorMessage>
                )}
              </>
            )}

            <NagContent>
              <textarea
                placeholder="잔소리를 작성해주세요"
                {...register('description', {
                  required: '❗ 잔소리를 입력해주세요 ❗',
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
              setSpecificTag={0}
              hashTagList={hashTagList}
              setHashTagList={setHashTagList}
            />
            <Button
              onClick={handleSubmit(onSubmit)}
              normal="true"
              label={'보내기'}
            />
          </NagFormContainer>
        </NagFormWrap>
      </XyzTransition>
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
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
  height: 100%;
  & > textarea {
    padding: 3vh;
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
