import React from 'react';
import TodoPost from './TodoPost';

const TodoPostList = (props) => {
  const { postList } = props;

  return (
    <div>
      <ul>
        {postList &&
          postList.map((post) => {
            return <TodoPost post={post} key={post.id} />; // TODO: key값 변경해줘야 함
          })}
      </ul>
    </div>
  );
};

export default TodoPostList;
