import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import HashTag from '../../components/HashTag/HashTag';
import Button from '../../components/UI/Button';
import { updateProfile } from '../../apis/api/member';
import { useNavigate } from 'react-router';
import {
  isLoginState,
  memberRoleState,
  profileImgState,
} from '../../states/user';
import { validateNickname, validateBio } from '../../utils/validate';
import { useRecoilValue, useSetRecoilState, useRecoilState } from 'recoil';
import { getAvailableNickname } from '../../apis/api/member';
import ProfileImg from '../../components/Profile/ProfileImg';
import { styled } from 'twin.macro';

const ProfileForm = (props) => {
  const { initial, prevNickname, prevBio, tags } = props;

  const [hashTagList, setHashTagList] = useState([]);
  const [checked, setChecked] = useState(false);
  const [checkError, setCheckError] = useState(false);
  const [nicknameValue, setNicknameValue] = useState('');
  const [isAvailable, setIsAvailable] = useState(false);

  const navigate = useNavigate();

  const isLogin = useRecoilValue(isLoginState);
  const setMemberRole = useSetRecoilState(memberRoleState);
  const [profileImg, setProfileImg] = useRecoilState(profileImgState);

  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    setValue,
  } = useForm({ mode: 'onBlur' });

  useEffect(() => {
    if (!tags) return;
    setHashTagList(tags);
    setValue('nickname', prevNickname);
    setValue('bio', prevBio);
  }, []);

  useEffect(() => {
    if (nicknameValue === '') return setIsAvailable(false);
    let timerId = setTimeout(async () => {
      // 닉네임 중복 검사 api 호출
      const data = await getAvailableNickname(nicknameValue);
      if (data.code === '200') {
        setIsAvailable(true);
      } else {
        setIsAvailable(false);
      }
    }, 500);

    return () => {
      clearTimeout(timerId);
    };
  }, [nicknameValue]);

  const handleUpdateProfile = async (data) => {
    if (initial && !checked) return setCheckError(true);

    const profile = {
      nickname: data.nickname,
      bio: data.bio,
      tags: hashTagList.length > 0 ? hashTagList.map((tag) => tag.tagId) : [-1],
    };

    console.log(profile);

    const formData = new FormData();

    formData.append('imageFile', profileImg);
    formData.append('memberInfo', JSON.stringify(profile));

    // 유저 정보 수정 api 호출
    const res = await updateProfile(profile);
    console.log(res);
    if (res.code === '200') {
      setMemberRole('USER');
      if (isLogin) navigate('/');
      else navigate('/login');
    }
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  const handleNicknameInputChange = (event) => {
    setNicknameValue(event.target.value);
  };

  const handleClickCheckboxLabel = () => {
    setChecked((prev) => {
      if (prev === false) setCheckError(false);
      return !prev;
    });
  };

  return (
    <FormContainer>
      <ProfileImg
        editable={true}
        profileImg={profileImg}
        setProfileImg={setProfileImg}
        size="80px"
      />
      <InfoContainer>
        <Label>닉네임</Label>
        <Nickname
          placeholder="닉네임을 입력해주세요."
          {...register('nickname', {
            defaultValue: prevNickname ? prevNickname : '',
            required: '닉네임을 입력해주세요.',
            maxLength: {
              value: 11,
              message: '11자 이하로 입력해주세요.',
            },
            validate: validateNickname,
          })}
          onChange={handleNicknameInputChange}
          onKeyDown={handleFormKeyDown}
        />
        {errors.nickname && nicknameValue === '' ? (
          <ErrorMessage>{errors?.nickname?.message}</ErrorMessage>
        ) : (
          <>
            {nicknameValue === '' && !isAvailable ? (
              <GuideMessage>1~11자 닉네임을 지어주세요.</GuideMessage>
            ) : (
              <>
                {isAvailable ? (
                  <GuideMessage granted="true">
                    사용 가능한 닉네임이에요!
                  </GuideMessage>
                ) : (
                  <ErrorMessage>이미 사용 중인 닉네임이에요...</ErrorMessage>
                )}
              </>
            )}
          </>
        )}
      </InfoContainer>
      <InfoContainer>
        <Label>소개글</Label>
        <Bio
          placeholder="소개글을 입력해주세요."
          {...register('bio', {
            defaultValue: prevBio ? prevBio : '',
            maxLength: {
              value: 200,
              message: '200자 이하로 입력해주세요.',
            },
            validate: validateBio,
          })}
          onKeyDown={handleFormKeyDown}
        />
        {errors.bio ? (
          <ErrorMessage>{errors.bio?.message}</ErrorMessage>
        ) : (
          <GuideMessage>200자 이내로 소개글을 작성할 수 있어요.</GuideMessage>
        )}
      </InfoContainer>
      <Label>해시태그 구독</Label>
      <HashTag
        editable={true}
        hashTagLimit={initial ? 3 : 1000}
        hashTagList={hashTagList}
        setHashTagList={setHashTagList}
      />
      {initial && (
        <GuideMessage>
          회원가입 시에는 최대 3개까지 등록할 수 있어요.
        </GuideMessage>
      )}
      {initial && (
        <>
          <RequestMessage>
            🌟 부적절한 언어나 다른 이용자들이 불쾌할 수 있는 잔소리는
            삼가주세요! 🌟
          </RequestMessage>
          <CheckboxContainer>
            <Checkbox type="checkbox" checked={checked} />
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
          {checkError && <ErrorMessage>확인 후 체크해주세요.</ErrorMessage>}
        </>
      )}
      <Footer>
        <Button
          onClick={handleSubmit(handleUpdateProfile)}
          normal="true"
          label={'완료'}
        />
      </Footer>
    </FormContainer>
  );
};

export default ProfileForm;

const FormContainer = styled.form`
  width: 40vw;
`;

const Label = styled.div`
  font-size: 14px;
  color: gray;
  font-weight: bold;
  margin-bottom: 3px;
`;

const InfoContainer = styled.div`
  margin-bottom: 10px;
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
  ${({ granted }) =>
    granted &&
    `
      color: #66bb6a
    `}
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
  height: 80px;
  margin: 0;
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
