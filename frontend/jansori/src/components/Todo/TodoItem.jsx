import React from 'react';
import tw, { styled } from 'twin.macro';
import HashTagItem from '../HashTag/HashTagItem';
import { useRecoilState } from 'recoil';
import { updateTodoComplete } from '../../apis/api/todo';
import { todoListState } from '../../states/todo';

const TodoItem = (props) => {
  const { currentTodo, onClick } = props;
  const [todoList, setTodoList] = useRecoilState(todoListState);

  const handleTodoClick = () => {
    onClick();
  };

  return (
    <TodoContainer>
      <TodoDone>
        <div className='finished' onClick={handleTodoClick}>
          {currentTodo.finished ? '✅' : '❌'}
        </div>
      </TodoDone>
      <TodoContent>
        <div className='todo'>{currentTodo.content}</div>
        <HashTagContent>
          {currentTodo.tags?.map((tag) => {
            return <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />;
          })}
        </HashTagContent>
      </TodoContent>
      <TodoExtendContent>
        <button>🔍</button>
      </TodoExtendContent>
    </TodoContainer>
  );
};

export default TodoItem;

const TodoContainer = styled.div`
  ${tw`
grid
grid-cols-5
gap-4
border-2
rounded
mt-1
py-2
bg-white`}
`;

const TodoDone = styled.button`
  ${tw`col-start-1
  items-center
  `}
`;

const TodoContent = styled.div`
  ${tw`col-span-3
  text-left`}
`;

const HashTagContent = styled.div`
  ${tw`text-xs`}
`;

const TodoExtendContent = styled.div`
  ${tw`col-span-1
  bg-slate-300
  rounded-lg
  mr-2
items-center
`}
`;
