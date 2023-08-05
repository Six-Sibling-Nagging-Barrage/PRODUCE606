import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import NagCommentItem from './NagCommentItem';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import commentIcon from '../../../assets/more_comment.png';

const TodoPost = (props) => {
  const { todoId, post, currentPostId, setCurrentPostId, setPersonaReaction } =
    props;

  const [showMoreSelected, setShowMoreSelected] = useState(false);

  useEffect(() => {
    if (currentPostId === todoId) {
      return setShowMoreSelected(true);
    }
    setShowMoreSelected(false);
  }, [currentPostId]);

  const handleClickShowMore = () => {
    // if (currentPostId === post.todoId) {
    if (currentPostId === todoId) {
      return setCurrentPostId(-1);
    }
    getPersonaReaction();
    // setCurrentPostId(post.todoId);
    setCurrentPostId(todoId);
  };

  const getPersonaReaction = () => {
    // Todo 상세조회 api 호출
    const data = {
      todoId: 101,
      finished: true,
      content: '집안일 하기',
      todoAt: '2023-08-01',
      tags: [
        {
          tagId: 4,
          tagName: '일상',
        },
      ],
      member: {
        memberId: 1,
        nickname: 'User001',
        imageUrl: 'https://example.com/user001.jpg',
      },
      nag: {
        nagId: 3,
        unlocked: false,
        isLiked: false,
        content: 'ㅇㅇㄴ',
        likeCount: 3,
        nagMember: {
          memberId: 2,
          nickname: 'User002',
          imageUrl: 'https://example.com/user002.jpg',
        },
      },
      personas: [
        {
          todoPersonaId: 1,
          personaId: 1,
          likeCount: 1,
          content: 'line1-1',
        },
        {
          todoPersonaId: 2,
          personaId: 2,
          likeCount: 0,
          content: null,
        },
        {
          todoPersonaId: 3,
          personaId: 3,
          likeCount: 1,
          content: 'line1-3',
        },
        {
          todoPersonaId: 4,
          personaId: 4,
          likeCount: 1,
          content: 'line1-4',
        },
        {
          todoPersonaId: 5,
          personaId: 5,
          likeCount: 1,
          content: 'line1-5',
        },
        {
          todoPersonaId: 6,
          personaId: 6,
          likeCount: 0,
          content: null,
        },
      ],
    };
    setPersonaReaction(data.personas);
  };

  return (
    <li>
      <PostContainer>
        <PostHeader>
          <ProfileLink href="#">
            <ProfileImage src={post.member.imageUrl} width="48" height="48" />
          </ProfileLink>
          <div>
            <WriterName>{post.member.nickname}</WriterName>
            <CreateDate>{post.todoAt}</CreateDate>
          </div>
          <PersonaReactionButton
            selected={showMoreSelected}
            onClick={handleClickShowMore}
          >
            <img src={commentIcon} />
          </PersonaReactionButton>
        </PostHeader>
        <TodoContent>
          <div className="finished">{post.finished ? '❌' : '✅'}</div>
          <div className="todo">{post.content}</div>
          <HashTagContainer>
            {post.tags.map((tag) => {
              return (
                <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />
              );
            })}
          </HashTagContainer>
        </TodoContent>
        <CommentsContainer>
          <NagCommentItem
            key={post.nag.nagId}
            isMemberNag={true}
            id={post.nag.nagId}
            isLiked={post.nag.isLiked}
            likeCount={post.nag.likeCount}
            content={post.nag.content}
            writer={post.nag.nagMember}
            nag={post.nag}
          />
        </CommentsContainer>
      </PostContainer>
    </li>
  );
};

const PostContainer = styled.div`
  ${tw`p-4 rounded-lg`}
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  margin: 10px;
`;
const PostHeader = styled.header`
  ${tw`flex gap-4 mb-4`}
  position: relative;
`;
const ProfileLink = tw.a`relative inline-flex items-center justify-center w-12 h-12 text-white rounded-full`;
const ProfileImage = tw.img`max-w-full rounded-full`;
const WriterName = styled.div`
  text-align: left;
`;
const CreateDate = tw.div`text-sm text-slate-400`;
const TodoContent = styled.div`
  text-align: center;
  padding: 20px 0;
  & .todo {
    padding-top: 20px;
  }
`;
const CommentsContainer = styled.div``;

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 10px auto;
  display: flex;
`;

const PersonaReactionButton = styled.button(
  ({ selected }) => `
  width: 35px;
  position: absolute;
  right: 0;
  ${
    selected &&
    'filter: invert(47%) sepia(96%) saturate(5568%) hue-rotate(239deg) brightness(103%) contrast(101%);'
  }
`
);

export default TodoPost;
