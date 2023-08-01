import React from 'react';
import { styled } from 'twin.macro';

const NagCommentItem = (props) => {
  const { comment } = props;

  return (
    <CommentContainer>
      <div className="profile-img">
        <img
          className="w-10 h-10 rounded-full"
          src={comment.img}
          alt="Rounded avatar"
        />
        <div>{comment.writer}</div>
      </div>
      <CommentContent>
        <div>{comment.content}</div>
      </CommentContent>
    </CommentContainer>
  );
};

const CommentContainer = styled.div`
  display: flex;
  margin-bottom: 10px;
  & .profile-img {
    width: 50px;
  }
`;
const CommentContent = styled.div`
  line-height: 64px;
  padding-left: 10px;
`;

export default NagCommentItem;
