import React from 'react';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import NagCommentList from './NagCommentList';
import profileImg from '../../../assets/profileImg.png';

const TodoPost = (props) => {
  const { post } = props;
  const commentList = [
    {
      id: 1,
      writer: '김민지',
      img: profileImg,
      content: '얼른해!!!',
    },
    {
      id: 2,
      writer: '강해린',
      img: profileImg,
      content: '얼른해!!!',
    },
  ];

  return (
    <li>
      <PostContainer>
        <PostHeader>
          <ProfileLink href="#">
            <ProfileImage src={post.writer.img} width="48" height="48" />
          </ProfileLink>
          <div>
            <WriterName>{post.writer.nickname}</WriterName>
            <CreateDate>{post.date}</CreateDate>
          </div>
        </PostHeader>
        <TodoContent>
          <div className="finished">{post.finished ? '❌' : '✅'}</div>
          <div className="todo">{post.content}</div>
          <div>해시태그 자리</div>
        </TodoContent>
        <PersonaReaction />
        <NagCommentList commentList={commentList} />
      </PostContainer>
    </li>
  );
};

const PostContainer = tw.div`
    p-6 rounded-lg shadow-lg
`;
const PostHeader = tw.header`flex gap-4 mb-4`;
const ProfileLink = tw.a`relative inline-flex items-center justify-center w-12 h-12 text-white rounded-full`;
const ProfileImage = tw.img`max-w-full rounded-full`;
const WriterName = tw.h3`text-base font-medium\ text-slate-700`;
const CreateDate = tw.p`text-sm text-slate-400`;
const TodoContent = styled.div`
  text-align: center;
  padding: 20px 0;
  & .todo {
    padding: 20px;
  }
`;
const hashTagContainer = styled.div`
  text-align: center;
  margin: 0 auto;
  width: wrap-content;
`;

export default TodoPost;
