import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import tw, { styled } from 'twin.macro';
import moment from 'moment';
import Mark from '../UI/Mark';
import Button from '../UI/Button';
import Toggle from '../UI/Toggle';
import HashTag from '../HashTag/HashTag';

const validateBio = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '연속된 공백 또는 앞뒤 공백은 사용할 수 없어요.';
  }
  return true;
};

const TodoForm = () => {
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    reset,
  } = useForm({ mode: 'onBlur' });

  const [content, setContent] = useState();
  const [isPublic, setIsPublic] = useState(true);
  const [hashTagList, setHashTagList] = useState([]);

  const todoFormSubmit = async (data) => {
    setContent('');
    const todo = {
      // display(공개여부), content(todo), todoAt(시간),
      // tags(tag - (tagId, tagName(tagId 없으면 - 1)))
      content: data.content,
      display: isPublic,
      todoAt: moment().format('YYYY-MM-DD'),
      tags: hashTagList.length > 0 ? hashTagList.map((tag) => tag.tagId) : [-1],
    };
    // TODO: todoinput 등록하는 api 호출
    // TODO: 성공했을 경우 밑에 등록되었다는 모달 띄우기(3초 후에 제거)
    // TODO: input form 들어가 있는 부분 제거
    reset();
    setIsPublic(true);
    console.log(todo);
  };

  const handleContentInputChange = (event) => {
    setContent(event.target.value);
  };

  //toglle 값 상태 변화
  const handleToggle = () => {
    setIsPublic(!isPublic);
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  const handleSubmitButton = (event) => {
    if (isSubmitting && content === '') {
      console.log('못 제출해');
    }
  };

  return (
    <TodoFormContainer>
      <Mark label={'todo'} />
      <TodoFormBox onKeyDown={handleFormKeyDown}>
        <TodoFormLabel>TO-DO</TodoFormLabel>
        <TodoFormInput>
          <TodoInput
            placeholder='Todo를 입력해주세요.'
            {...register('content', {
              required: 'Todo를 입력해주세요.',
              minLength: {
                value: 2,
                message: '2글자 이상으로 입력해주세요.',
              },
              maxLength: {
                value: 30,
                message: '30글자 이하로 입력해주세요.',
              },
              validate: validateBio,
            })}
            onChange={handleContentInputChange}
          />
        </TodoFormInput>
        <TodoFormLabel>해시태그</TodoFormLabel>
        <TodoFormInput>
          <HashTag
            editable={true}
            hashTagLimit={3}
            hashTagList={hashTagList}
            setHashTagList={setHashTagList}
          />
        </TodoFormInput>
        <TodoFormLabel>공개여부</TodoFormLabel>
        <TodoFormInput>
          <Toggle isPublic={isPublic} onToggle={handleToggle} todoInput />
        </TodoFormInput>
        <TodoFormLabel>날짜</TodoFormLabel>
        <TodoFormInput>
          <DateWrap>{moment().format('YYYY-MM-DD')}</DateWrap>
        </TodoFormInput>

        <ErrorMessage>
          {errors.content ? (
            <ErrorText>⭐ {errors?.content?.message} ⭐</ErrorText>
          ) : (
            <>
              <ErrorText>
                {content === '' && isSubmitting
                  ? '⭐ todo는 2글자에서 30글자 이하로 작성해주세요! ⭐'
                  : '👊 열심히 달성해보아요! 👊'}
              </ErrorText>
            </>
          )}
        </ErrorMessage>
        <ButtonLocation>
          <Button
            onClick={handleSubmit(todoFormSubmit)}
            disabled={isSubmitting && content === ''}
            type='submit'
            label={'Add'}
            normal
          />
        </ButtonLocation>
      </TodoFormBox>
    </TodoFormContainer>
  );
};

export default TodoForm;

const TodoFormContainer = styled.div`
  ${tw`
    relative
    bg-white
    rounded-lg
    w-2/5
    border-2
    pb-8`}

  @media (min-width: 990px) and (max-width: 1200px) {
    width: 50%;
  }
  @media (min-width: 786px) and (max-width: 990px) {
    width: 60%;
  }
  @media (min-width: 680px) and (max-width: 786px) {
    width: 70%;
  }
  @media (max-width: 680px) {
    width: 90%;
  }
`;

const TodoFormBox = styled.form`
  ${tw`grid grid-cols-3 gap-4
  w-11/12
  mx-auto
  `}
`;

const TodoFormLabel = styled.label`
  ${tw`m-auto flex items-center text-center`}
`;

const TodoFormInput = styled.div`
  ${tw`col-span-2`}
`;

const ErrorMessage = styled.div`
  ${tw`col-start-1 col-end-4
    flex justify-center items-center w-full h-16`}
`;

const ErrorText = styled.span`
  ${tw`block text-center mx-auto text-sm`}
`;

const ButtonLocation = styled.div`
  ${tw`absolute
    bottom-2
    right-2`}
`;

const TodoInput = styled.input`
  ${tw`
    w-full
    px-3 py-2
    text-gray-700
    bg-white
    border border-gray-300
    rounded-md
    focus:outline-none
    focus:border-blue-300
    transition duration-300 ease-in-out
  `}
`;

const DateWrap = styled.div`
  ${tw`text-left`}
`;
