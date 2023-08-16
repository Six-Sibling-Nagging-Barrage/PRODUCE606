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
import NagItem from '../../ProfilePage/components/NagItem';
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
  const [isHidden, setIsHidden] = useState(false);
  const [nag, setNag] = useState(null);

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
      tagName: hashTagList[0].tagName,
    };

    setNag({
      content: data.description,
      likeCount: 0,
      deliveredCount: 0,
      tag: {
        tagId: hashTagList[0].tagId,
        tagName: hashTagList[0].tagName,
      },
    });

    const response = await createNag(nag);
    if (response.code === '200') {
      setIsHidden(true);
      setTimeout(() => setIsSubmitted(true), 1000);
      setTicket(response.data.ticketCount);
      setSnackBarMessage('잔소리를 성공적으로 보냈어요! 티켓 1장 획득!');
      setShowSnackBar(true);
    } else {
      setSnackBarMessage('잔소리 전송에 실패했어요...');
      setShowSnackBar(true);
    }
    setCheckSubmitted(false); //원래 상태로 복구
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

  const handleClickOk = () => {
    setIsSubmitted(false);
    setIsHidden(false);
  };

  return (
    <div>
      <XyzTransition appear duration="auto" xyz="fade up-100% duration-10">
        <NagFormWrap isHidden={isHidden}>
          <NagFormTitle>잔소리 보내기</NagFormTitle>
          <NagFormContainer>
            {errors?.description ? (
              <ErrorMessage>{errors?.description?.message}</ErrorMessage>
            ) : (
              <>
                {nagValue === '' ? (
                  <ErrorMessage>
                    📛 상처 주는 말이 아닌 동기부여가 될 수 있는 잔소리를
                    적어주세요! 📛
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
              creatable={true}
              hashTagLimit={1}
              setSpecificTag={0}
              hashTagList={hashTagList}
              setHashTagList={setHashTagList}
            />
            <Footer>
              <Button onClick={handleSubmit(onSubmit)} normal="true">
                보내기
              </Button>
            </Footer>
          </NagFormContainer>
        </NagFormWrap>
      </XyzTransition>
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
      {isSubmitted && nag && (
        <Preview>
          <Waiting>
            👀 . . . 지금 내 잔소리는 TODO를 찾아 떠나는 중 . . . 👀
          </Waiting>
          <NagPreview>
            <NagItem isMine={true} nag={nag} width={'100%'} />
          </NagPreview>
          <Message>
            내가 쓴 잔소리가 다른 사람 TODO에 도착하면 알려드릴게요!
          </Message>
          <Button onClick={handleClickOk} normal={true}>
            잘가 잔소리야..!
          </Button>
        </Preview>
      )}
    </div>
  );
};

export default NagForm;

const NagFormWrap = styled.div`
  position: absolute;
  top: 45%;
  left: 50%;
  width: 40%;
  @media screen and (max-width: 768px) {
    width: 70%;
  }
  @media screen and (max-width: 1024px) {
    width: 50%;
  }
  background-color: white;
  transform: translate(-50%, -50%);
  padding: 50px;
  z-index: 30;
  border-radius: 50%;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.1);
  transition: all 1s ease;
  font-size: 16px;
  ${({ isHidden }) =>
    isHidden &&
    `
    opacity: 0;
    visibility: hidden;
  `}

  &:after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 0;
    border: 30px solid transparent;
    border-top-color: #ffffff;
    border-bottom: 0;
    border-left: 0;
    margin-left: -15px;
    margin-bottom: -28px;
  }
`;

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
    border: 1px solid #e1e1e1;
  }
`;

const Preview = styled.div`
  position: absolute;
  top: 45%;
  left: 50%;
  width: 40%;
  padding: 35px;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.2);
  @media screen and (max-width: 768px) {
    width: 70%;
  }
  @media screen and (max-width: 1024px) {
    width: 50%;
  }
  background-color: #ede7f6;
  transform: translate(-50%, -50%);
  border-radius: 30px;
`;

const NagPreview = styled.div`
  margin: 20px auto;
  width: 60%;
`;

const Waiting = styled.div`
  font-size: 18px;
  height: 35px;
  font-weight: 600;
  @keyframes motionAnimation {
    0% {
      padding-top: 0px;
    }
    100% {
      padding-top: 10px;
    }
  }
  animation: motionAnimation 1s linear 0s infinite alternate;
`;

const Message = styled.div`
  margin-bottom: 15px;
`;

const ErrorMessage = styled.div`
  margin: 3vh 0;
  font-size: 14px;
  color: gray;
`;

const Footer = styled.div`
  margin-top: 30px;
  margin-bottom: 20px;
`;
