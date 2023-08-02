import React from 'react';
import TodoPost from './TodoPost';

const TodoPostList = (props) => {
  const { todoPostList } = props;

  return (
    <div>
      <ul>
        {todoPostList &&
          todoPostList.map((post, index) => {
            return <TodoPost post={post} key={index} />; // TODO: key값 변경해줘야 함
          })}
      </ul>
    </div>
  );
};

export default TodoPostList;
