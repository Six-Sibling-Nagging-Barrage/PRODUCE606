import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import Background from '../../components/UI/Background';
import { styled } from 'twin.macro';
import ProfileImg from '../../components/Profile/ProfileImg';
import HashTag from '../../components/HashTag/HashTag';
import Button from '../../components/UI/Button';

const validateNickname = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '연속된 공백 또는 앞뒤 공백은 사용할 수 없어요.';
  }
  if (!/^[a-zA-Z0-9_\sㄱ-ㅎㅏ-ㅣ가-힣]+$/.test(value)) {
    return '한글, 영문, 숫자, _, 공백만 사용할 수 있어요.';
  }
  return true;
};

const validateBio = (value) => {
  if (/\s{2,}/.test(value)) {
    return '연속된 공백을 사용할 수 없어요.';
  }
  return true;
};

const SignUpPage = () => {
  const [hashTagList, setHashTagList] = useState([]);
  const [checked, setChecked] = useState(false);
  const [nicknameValue, setNicknameValue] = useState('');

  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
  } = useForm({ mode: 'onBlur' });

  const signupSubmit = (data) => {
    if (!checked) {
      return document.getElementById('promise-checkbox').focus();
    }

    const profile = {
      nickname: data.nickname,
      bio: data.bio,
      imageUrl: 'https://example.com/image.jpg',
      tags: hashTagList.length > 0 ? hashTagList.map((tag) => tag.tagId) : [-1],
    };
    // TODO: 회원 가입 api 호출
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  const handleNicknameInputChange = (event) => {
    setNicknameValue(event.target.value);
    // TODO: 닉네임 중복 검사 api 호출
  };

  const handleClickCheckboxLabel = () => {
    setChecked((prev) => !prev);
  };

  return (
    <Background>
      <SignUpContainer>
        <SignUpTitle></SignUpTitle>
        <form onKeyDown={handleFormKeyDown}>
          <ProfileImg editable={true} />
          <InfoContainer>
            <Label value={nicknameValue} onChange={handleNicknameInputChange}>
              닉네임
            </Label>
            <Nickname
              placeholder="닉네임을 입력해주세요."
              {...register('nickname', {
                required: '닉네임을 입력해주세요.',
                maxLength: {
                  value: 11,
                  message: '11자 이하로 입력해주세요.',
                },
                validate: validateNickname,
              })}
            />
            {errors.nickname ? (
              <ErrorMessage>{errors?.nickname?.message}</ErrorMessage>
            ) : (
              <GuideMessage>1~11자 닉네임을 지어주세요.</GuideMessage>
            )}
          </InfoContainer>
          <InfoContainer>
            <Label>소개글</Label>
            <Bio
              placeholder="소개글을 입력해주세요."
              {...register('bio', {
                maxLength: {
                  value: 200,
                  message: '200자 이하로 입력해주세요.',
                },
                validate: validateBio,
              })}
            />
            {errors.bio ? (
              <ErrorMessage>{errors.bio?.message}</ErrorMessage>
            ) : (
              <GuideMessage>
                200자 이내로 소개글을 작성할 수 있어요.
              </GuideMessage>
            )}
          </InfoContainer>
        </form>
        <Label>해시태그 구독</Label>
        <HashTag
          hashTagLimit={3}
          hashTagList={hashTagList}
          setHashTagList={setHashTagList}
        />
        <GuideMessage>
          회원가입 시에는 최대 3개까지 등록할 수 있어요.
        </GuideMessage>
        <RequestMessage>
          🌟 부적절한 언어나 다른 이용자들이 불쾌할 수 있는 잔소리는 삼가주세요!
          🌟
        </RequestMessage>
        <CheckboxContainer>
          <Checkbox
            type="checkbox"
            {...register('checkbox', { required: true })}
            checked={checked}
          />
          <CustomCheckbox>
            <CheckboxLabel
              onClick={handleClickCheckboxLabel}
              checked={checked}
            ></CheckboxLabel>
            <div className="label" onClick={handleClickCheckboxLabel}>
              확인했어요 ✔
            </div>
          </CustomCheckbox>
        </CheckboxContainer>
        {errors.checkbox && !checked && (
          <ErrorMessage>확인 후 체크해주세요.</ErrorMessage>
        )}
        <Footer>
          <Button
            onClick={handleSubmit(signupSubmit)}
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
  @media screen and (max-width: 768px) {
    width: 70%;
  }
  @media screen and (max-width: 1024px) {
    width: 50%;
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

const ErrorMessage = styled.div`
  font-size: 14px;
  color: #ff6c6c;
  margin-top: 5px;
`;

const GuideMessage = styled.div`
  font-size: 14px;
  color: #757575;
  margin-top: 5px;
`;

const RequestMessage = styled.div`
  margin-top: 20px;
  margin-bottom: 5px;
  font-weight: bold;
  font-style: italic;
`;

const Nickname = styled.input`
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

const CheckboxContainer = styled.div`
  display: flex;
`;

const CustomCheckbox = styled.div`
  margin: 0 auto;
  margin-top: 5px;
  position: relative;
  & .label {
    margin-left: 40px;
    line-height: 28px;
  }
  & .label:hover {
    cursor: pointer;
  }
`;

const Checkbox = styled.input`
  visibility: hidden;
  width: 0;
`;

const CheckboxLabel = styled.label`
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 50%;
  cursor: pointer;
  width: 28px;
  height: 28px;
  position: absolute;
  left: 0;
  top: 0;
  ${({ checked }) =>
    checked
      ? `
          background-color: #66bb6a;
          border-color: #66bb6a;
          &:after {
            border: 2px solid #fff;
            border-top: none;
            border-right: none;
            content: '';
            height: 6px;
            left: 7px;
            position: absolute;
            top: 8px;
            transform: rotate(-45deg);
            width: 12px;
          }
        `
      : `
          background-color: #fff !important;
          &:after {
            opacity: 1;
          }
        `}
`;

const Footer = styled.div`
  margin: 10px 0 20px;
`;

export default SignUpPage;
