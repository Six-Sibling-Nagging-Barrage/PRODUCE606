import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import tw, { styled } from 'twin.macro';
import moment from 'moment';
import Mark from '../UI/Mark';
import Button from '../UI/Button';
import Toggle from '../UI/Toggle';
import HashTag from '../HashTag/HashTag';

const validateBio = (value) => {
  if (/\s{2,}/.test(value)) {
    return '연속된 공백을 사용할 수 없어요.';
  }
  return true;
};

const TodoForm = () => {
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
  } = useForm({ mode: 'onBlur' });

  // const [todo, setTodo] = useState();
  const [isPublic, setIsPublic] = useState(true);
  const [hashTagList, setHashTagList] = useState([]);

  const todoFormSubmit = async (data) => {
    const todo = {
      // display(공개여부), content(todo), todoAt(시간),
      // tags(tag - (tagId, tagName(tagId 없으면 - 1)))
      content: data.content,
      display: isPublic,
      todoAt: moment().format('YYYY-MM-DD'),
      tags: hashTagList.length > 0 ? hashTagList.map((tag) => tag.tagId) : [-1],
    };
    console.log(todo);
  };

  const handleToggle = () => {
    setIsPublic(!isPublic);
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  return (
    <TodoFormContainer>
      <Mark label={'todo'} />
      <TodoFormBox onKeyDown={handleFormKeyDown}>
        <label>TO-DO</label>
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
          />
        </TodoFormInput>
        <label>해시태그</label>
        <TodoFormInput>
          <HashTag
            editable={true}
            hashTagLimit={3}
            hashTagList={hashTagList}
            setHashTagList={setHashTagList}
          />
        </TodoFormInput>
        <label>공개여부</label>
        <TodoFormInput>
          <Toggle isPublic={isPublic} onToggle={handleToggle} todoInput />
        </TodoFormInput>
        <label>날짜</label>
        <TodoFormInput>
          <span>{moment().format('YYYY-MM-DD')}</span>
        </TodoFormInput>

        <ErrorMessage>
          <ErrorText>
            ⭐ {errors?.todo ? errors.message : 'todo는 2글자 이상 30글자 이내로 작성해주세요.'} ⭐
          </ErrorText>
        </ErrorMessage>
        <ButtonLocation>
          <Button
            onClick={handleSubmit(todoFormSubmit)}
            disabled={isSubmitting}
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
  bg-red-300
  rounded-lg
  w-2/5
  border-2
  pb-16`}

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
`}
`;

const TodoFormInput = styled.div`
  ${tw`col-span-2`}
`;

const ErrorMessage = styled.div`
  ${tw`col-start-1 col-end-4
   flex justify-center items-center w-full h-16`}
`;

const ErrorText = styled.span`
  ${tw`block text-center mx-auto`}
`;

const ButtonLocation = styled.div`
  ${tw`absolute
  bottom-2
  right-2`}
`;

const TodoInput = styled.input`
  ${`w-full`}
`;
