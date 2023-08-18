import React, { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import tw, { styled } from 'twin.macro';
import moment from 'moment';
import Mark from '../UI/Mark';
import Button from '../UI/Button';
import Toggle from '../UI/Toggle';
import HashTag from '../HashTag/HashTag';
import { createTodo, getTodoListByDate, getTodoListByDateByMember } from '../../apis/api/todo';
import { useSetRecoilState, useRecoilValue, useRecoilState } from 'recoil';
import {
  focusDateState,
  todoListState,
  todoInputFormContent,
  todoInputFormHashTag,
  focusYearMonthState,
  todoDaysState,
} from '../../states/todo';
import { memberIdState } from '../../states/user';
import SnackBar from '../../components/UI/SnackBar';

const validateBio = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '연속된 공백 또는 앞뒤 공백은 사용할 수 없어요.';
  }
  return true;
};

// TODO: hashtag 길이가 0일 경우 form 넘어가지 않도록 초기 설정 해주기
// TODO : 전송하고 난 다음 hashtag count 초기화 되는 부분 설정
const TodoForm = () => {
  const {
    register,
    formState: { errors, isSubmitted },
    handleSubmit,
    reset,
  } = useForm({ mode: 'onBlur' });

  const [content, setContent] = useState();
  const [isPublic, setIsPublic] = useState(true);
  const [hashTagList, setHashTagList] = useState([]);
  const [isHashTagList, setIsHashTagList] = useState(false);
  const setDate = useSetRecoilState(focusDateState);
  const [yearMonth, setYearMonth] = useRecoilState(focusYearMonthState);
  const setTodoList = useSetRecoilState(todoListState);
  const setYearMonthTodoList = useSetRecoilState(todoDaysState);
  const [todoInputFormContentValue, setTodoInputFormContentValue] =
    useRecoilState(todoInputFormContent);
  const [todoInputFormHasTagValue, setTodoInputFormHasTagValue] =
    useRecoilState(todoInputFormHashTag);
  const memberId = useRecoilValue(memberIdState);

  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  useEffect(() => {
    setContent(todoInputFormContentValue);
    setHashTagList(todoInputFormHasTagValue);
  }, [todoInputFormContentValue]);

  const todoFormSubmit = async (data) => {
    setIsHashTagList(true);
    if (content === '' || hashTagList.length === 0) return;
    setIsHashTagList(false);
    const todo = {
      content: data.content,
      display: isPublic,
      todoAt: moment().format('YYYY-MM-DD'),
      tags: hashTagList.map((tag) => ({
        tagId: tag.tagId,
        tagName: tag.tagName,
      })),
    };
    const response = await createTodo(todo);
    if (response.code === '200') {
      setSnackBarMessage('TODO가 등록되었어요. 파이팅!');
      setShowSnackBar(true);
      setHashTagList([]);
      setTodoInputFormHasTagValue([]);
      setTodoInputFormContentValue('');
      setContent('');
      setIsPublic(true);
      reset();

      const newDate = moment(new Date()).format('YYYY-MM-DD');
      setDate(newDate);
      // 변경에 해당하는 api 호출
      const todoListResult = await getTodoListByDate(newDate);
      if (todoListResult.code === '200') {
        setTodoList(todoListResult.data.todos);
      }

      //캘린더 업데이트
      const newYearMonth = moment(new Date()).format('YYYY-MM');
      setYearMonth(newYearMonth);
      const todoMonthListResult = await getTodoListByDateByMember({
        memberId: memberId,
        date: yearMonth,
      });
      if (todoMonthListResult.code === '200') {
        setYearMonthTodoList(todoMonthListResult.data.dates);
      }
    }
  };

  const handleContentInputChange = (event) => {
    setContent(event.target.value);
  };

  //toggle 값 상태 변화
  const handleToggle = () => {
    setIsPublic(!isPublic);
  };

  const handleFormKeyDown = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();
    }
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <TodoFormContainer>
      <MarkWrap>
        <Mark label={'TODO'} />
      </MarkWrap>
      <TodoFormBox>
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
            value={content}
            onChange={handleContentInputChange}
            onKeyDown={handleFormKeyDown}
          />
        </TodoFormInput>
        <TodoFormLabel>해시태그</TodoFormLabel>
        <TodoFormInput>
          <HashTag
            editable={true}
            creatable={true}
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
                {isHashTagList && isSubmitted
                  ? '💥 해시태그가 없어요 💥'
                  : '👊 열심히 달성해보아요! 👊'}
              </ErrorText>
            </>
          )}
        </ErrorMessage>
        <ButtonLocation>
          <Button onClick={handleSubmit(todoFormSubmit)} normal='true'>
            등록
          </Button>
        </ButtonLocation>
      </TodoFormBox>
      {showSnackBar && <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />}
    </TodoFormContainer>
  );
};

export default TodoForm;

const TodoFormContainer = styled.div`
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
  ${tw`
    relative
    bg-white
    mb-3
    pb-8`}
`;

const TodoFormBox = styled.form`
  ${tw`grid grid-cols-3 gap-4
  w-11/12
  mx-auto
  `}
`;

const MarkWrap = styled.div`
  padding-top: 0.5vh;
  padding-left: 0.5vh;
`;

const TodoFormLabel = styled.label`
  ${tw`m-auto flex items-center text-center text-sm`}
`;

const TodoFormInput = styled.div`
  ${tw`col-span-2 text-sm`}
`;

const ErrorMessage = styled.div`
  ${tw`col-start-1 col-end-4
   text-sm
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
