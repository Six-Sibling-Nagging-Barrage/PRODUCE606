import React from 'react';
import { useForm } from 'react-hook-form';
import tw, { styled } from 'twin.macro';
import HashTag from '../../../components/HashTag/HashTag';
import Button from '../../../components/UI/Button';

const NagForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ mode: 'onBlur' });

  return (
    <div>
      <NagFormTitle>잔소리 보내기</NagFormTitle>
      <NagFormContainer>
        <NagContent>
          <textarea
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
                value: /^(?!.*\s\s)[[\u1100-\u1112\u3131-\u314e\u314f-\u3163가-힣]+$/,
                message: '공백 문자를 연속 2개 이상 사용할 수 없어요.',
              },
            })}
          />
          <ErrorMessage>{errors?.description?.message}</ErrorMessage>
        </NagContent>
        <HashTag hashTagLimit={3} />
        <Button normal label={'완료'} />
      </NagFormContainer>
    </div>
  );
};

export default NagForm;

const NagFormTitle = styled.div`
  ${tw`text-center text-3xl font-bold m-6`}
`;

const NagFormContainer = styled.form`
  ${tw`w-full max-w-md mx-auto`}
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
  margin-top: 5px;
  font-size: 14px;
  color: gray;
`;
