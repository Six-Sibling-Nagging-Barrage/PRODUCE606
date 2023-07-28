import React from 'react';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import NagCommentList from './NagCommentList';

const TodoPost = (props) => {
  const { post } = props;
  const commentList = [
    {
      writer: '김민지',
      img: 'https://i.pravatar.cc/48?img=24',
      content: '얼른해!!!',
    },
    {
      writer: '강해린',
      img: 'https://i.pravatar.cc/48?img=24',
      content: '얼른해!!!',
    },
  ];

  return (
    <li>
      <PostContainer>
        <PostHeader>
          <ProfileLink href='#'>
            <ProfileImage
              src='https://i.pravatar.cc/48?img=24'
              alt='user name'
              title='user name'
              width='48'
              height='48'
            />
          </ProfileLink>
          <div>
            <WriterName>{post.writer}</WriterName>
            <CreateDate>{post.date}</CreateDate>
          </div>
        </PostHeader>
        <TodoContent>
          <div>{post.finished ? '❌' : '✅'}</div>
          <div>{post.content}</div>
          <div>해시태그 자리</div>
        </TodoContent>
        <PersonaReaction />
        <NagCommentList commentList={commentList} />
      </PostContainer>
    </li>
  );
};

const PostContainer = tw.div`
    p-6 rounded-lg border border-gray-400
`;
const PostHeader = tw.header`flex gap-4 mb-4`;
const ProfileLink = tw.a`relative inline-flex items-center justify-center w-12 h-12 text-white rounded-full`;
const ProfileImage = tw.img`max-w-full rounded-full`;
const WriterName = tw.h3`text-xl font-medium text-slate-700`;
const CreateDate = tw.p`text-sm text-slate-400`;
const TodoContent = styled.div`
  text-align: center;
  padding: 20px 0;
`;

export default TodoPost;
