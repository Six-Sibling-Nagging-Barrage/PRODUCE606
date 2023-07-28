import React from 'react';
import { styled } from 'twin.macro';

const NagCommentItem = (props) => {
  const { comment } = props;

  return (
    <li>
      <CommentContainer>
        <div className='profile-img'>
          <img
            class='w-10 h-10 rounded-full'
            src={comment.img}
            alt='Rounded avatar'
          />
          <div>{comment.writer}</div>
        </div>
        <div>
          <div>{comment.content}</div>
        </div>
      </CommentContainer>
    </li>
  );
};

const CommentContainer = styled.div`
  display: flex;
  margin-bottom: 10px;
  & .profile-img {
    width: 50px;
  }
`;

export default NagCommentItem;
