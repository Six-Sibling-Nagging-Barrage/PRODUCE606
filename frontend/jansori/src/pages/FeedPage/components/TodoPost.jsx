import React, { useEffect, useState } from 'react';
import tw, { styled } from 'twin.macro';
import NagCommentItem from './NagCommentItem';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import { getTodoDetail } from '../../../apis/api/todo';
import { Link } from 'react-router-dom';
import { personas } from '../../../constants/persona';
import { useImageErrorHandler } from '../../../hooks/useImageErrorHandler';
import { altImageUrl } from '../../../constants/image';
import todoDone from '../../../assets/todo_done.png';
import todoNotDone from '../../../assets/todo_not_done.png';

const TodoPost = (props) => {
  const {
    post,
    currentPostId,
    setCurrentPostId,
    setCurrentPost,
    setPersonaReaction,
    toggleLike,
    toggleUnlock,
    randomPersona,
  } = props;

  const [showMoreSelected, setShowMoreSelected] = useState(false);

  const handleImgError = useImageErrorHandler();

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
    setCurrentPostId(post.todoId);
    setCurrentPost(post);
    getPersonaReaction();
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
            <ProfileImage
              src={post.member.imageUrl ? post.member.imageUrl : altImageUrl}
              onError={handleImgError}
            />
          </Link>
          <div>
            <WriterName>{post.member.nickname}</WriterName>
            <CreateDate>{post.todoAt}</CreateDate>
          </div>
          {showMoreSelected ? (
            <SelectedPersonaReactionButton onClick={handleClickShowMore}>
              <img src={personas[randomPersona].imgUrl} />
            </SelectedPersonaReactionButton>
          ) : (
            <PersonaReactionButton onClick={handleClickShowMore}>
              <img src={personas[randomPersona].imgUrl} />
            </PersonaReactionButton>
          )}
        </PostHeader>
        <TodoContent>
          <div className="finished">
            {post.finished ? (
              <TodoToggleButton src={todoDone} />
            ) : (
              <TodoToggleButton src={todoNotDone} />
            )}
          </div>
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
          {post.nag && (
            <NagCommentItem
              key={post.nag.nagId}
              isMemberNag={true}
              todoId={post.todoId}
              nag={post.nag}
              toggleLike={toggleLike}
              toggleUnlock={toggleUnlock}
            />
          )}
        </div>
      </PostContainer>
    </li>
  );
};

const PostContainer = styled.div`
  ${tw`p-4 pb-1.5`}
  border-radius: 20px;
  margin: 0 auto;
  margin-bottom: 15px;
  background-color: white;
  box-shadow: 0 0 10px rgba(163, 163, 163, 0.2);
`;

const PostHeader = styled.header`
  ${tw`flex gap-4`}
  position: relative;
`;

const ProfileImage = styled.img`
  object-fit: cover;
  width: 48px;
  height: 48px;
  ${tw`max-w-full rounded-full`}
`;

const WriterName = styled.div`
  text-align: left;
`;

const CreateDate = styled.div`
  ${tw`text-sm text-slate-400`}
  text-align:left;
`;

const TodoContent = styled.div`
  text-align: center;
  margin: 10px;
  & .todo {
    margin: 10px 0;
  }
`;

const TodoToggleButton = styled.img`
  width: 40px;
  margin: 0 auto;
`;

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 0 auto;
  display: flex;
`;

const PersonaReactionButton = styled.button`
  ${tw`w-12 h-12 rounded-full`}
  position: absolute;
  right: 0;
  &:hover {
    ${tw`w-14 h-14`}
  }
`;

const SelectedPersonaReactionButton = styled.button`
  ${tw`w-12 h-12 rounded-full`}
  position: absolute;
  right: 0;
  ${tw`w-14 h-14`}
`;

export default TodoPost;
