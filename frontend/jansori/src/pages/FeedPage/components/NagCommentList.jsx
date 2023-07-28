import React from 'react';
import { styled } from 'twin.macro';
import NagCommentItem from './NagCommentItem';

const NagCommentList = (props) => {
  const { commentList } = props;

  return (
    <CommentsContainer>
      {commentList &&
        commentList.map((comment) => {
          return <NagCommentItem comment={comment} />;
        })}
    </CommentsContainer>
  );
};

const CommentsContainer = styled.div`
  margin-top: 20px;
`;

export default NagCommentList;
