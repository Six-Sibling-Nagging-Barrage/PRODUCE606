import React from 'react';
import { styled } from 'twin.macro';

const NagCommentItem = (props) => {
  const { id, like, content, img } = props;

  return (
    <CommentContainer>
      <div className="profile-img">
        <img
          className="w-10 h-10 rounded-full"
          src={img}
          alt="Rounded avatar"
        />
        <div>{id}</div>
      </div>
      <CommentContent>
        <div>{content}</div>
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
