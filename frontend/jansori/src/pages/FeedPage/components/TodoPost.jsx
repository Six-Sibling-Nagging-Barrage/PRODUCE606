import React from 'react';
import tw from 'twin.macro';
import TodoContent from './TodoContent';
import PersonaReaction from './PersonaReaction';
import NagComment from './NagComment';

const TodoPost = (props) => {
  const { post } = props;

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
            <WriterName>팜하니</WriterName>
            <CreateDate>2023.07.26</CreateDate>
          </div>
        </PostHeader>
        <TodoContent />
        <PersonaReaction />
        <NagComment />
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

export default TodoPost;
