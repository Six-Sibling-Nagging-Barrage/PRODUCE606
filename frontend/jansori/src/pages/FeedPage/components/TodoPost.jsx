import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import NagCommentItem from './NagCommentItem';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import commentIcon from '../../../assets/more_comment.png';
import { getTodoDetail } from '../../../apis/api/todo';
import { Link } from 'react-router-dom';

const TodoPost = (props) => {
  const {
    post,
    currentPostId,
    setCurrentPostId,
    setPersonaReaction,
    toggleLike,
    toggleUnlock,
  } = props;

  const [showMoreSelected, setShowMoreSelected] = useState(false);

  useEffect(() => {
    if (currentPostId === post.todoId) {
      return setShowMoreSelected(true);
    }
    setShowMoreSelected(false);
  }, [currentPostId]);

  const handleClickShowMore = () => {
    if (currentPostId === post.todoId) {
      return setCurrentPostId(-1);
    }
    getPersonaReaction();
    setCurrentPostId(post.todoId);
  };

  const getPersonaReaction = () => {
    // Todo 상세조회 api 호출
    (async () => {
      const data = await getTodoDetail(post.todoId);
      setPersonaReaction(data.data.personas);
    })();
  };

  return (
    <li>
      <PostContainer>
        <PostHeader>
          <Link to={`/profile?id=${encodeURIComponent(post.member.memberId)}`}>
            <ProfileImage src={post.member.imageUrl} width="48" height="48" />
          </Link>
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
          <div className="finished">{post.finished ? '✅' : '❌'}</div>
          <div className="todo">{post.content}</div>
          <HashTagContainer>
            {post.tags?.map((tag) => {
              return (
                <HashTagItem key={tag.tagId} hashTag={tag} editable={false} />
              );
            })}
          </HashTagContainer>
        </TodoContent>
        <div>
          <NagCommentItem
            key={post.nag.nagId}
            isMemberNag={true}
            todoId={post.todoId}
            nag={post.nag}
            toggleLike={toggleLike}
            toggleUnlock={toggleUnlock}
          />
        </div>
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
  ${tw`flex gap-4`}
  position: relative;
`;

// const ProfileLink = styled.Link`
//   relative inline-flex items-center justify-center w-12 h-12 text-white rounded-full
// `;

const ProfileImage = styled.img`
  max-w-full rounded-full
`;
const WriterName = styled.div`
  text-align: left;
`;

const CreateDate = styled.div`
  text-sm text-slate-400
`;

const TodoContent = styled.div`
  text-align: center;
  margin: 10px;
  & .todo {
    margin: 10px 0;
  }
`;

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 0 auto;
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
