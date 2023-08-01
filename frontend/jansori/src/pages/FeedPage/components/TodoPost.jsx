import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import profileImg from '../../../assets/profileImg.png';
import showMoreImg from '../../../assets/show_more.png';
import hideImg from '../../../assets/hide.png';
import NagCommentItem from './NagCommentItem';

const personaNagData = [
  {
    personaId: 1,
    likeCount: 1,
    todoPersonaId: 1,
    content: 'line1',
  },
  {
    personaId: 2,
    likeCount: 1,
    todoPersonaId: 2,
    content: 'line2',
  },
  {
    personaId: 3,
    likeCount: 1,
    todoPersonaId: 3,
    content: 'line3',
  },
  {
    personaId: 4,
    likeCount: 1,
    todoPersonaId: 4,
    content: 'line4',
  },
  {
    personaId: 5,
    likeCount: 1,
    todoPersonaId: 5,
    content: 'line5',
  },
  {
    personaId: 6,
    likeCount: 1,
    todoPersonaId: 6,
    content: 'line6',
  },
];

const TodoPost = (props) => {
  const { post } = props;
  const [showMore, setShowMore] = useState(false);
  const [personaNagList, setPersonaNagList] = useState([]);

  const handleClickShowMore = () => {
    if (!showMore) {
      getPersonaNagList();
    }
    setShowMore((prev) => !prev);
  };

  const getPersonaNagList = () => {
    // 캐릭터 잔소리 더보기 api 호출
    // /todo/{todoId}/personas
    // if (personaNagList.length === 0) {
    //   setPersonaNagList(personaNagData);
    // }
  };

  return (
    <li>
      <PostContainer>
        <PostHeader>
          <ProfileLink href="#">
            <ProfileImage
              src={post.todoMember.imageUrl}
              width="48"
              height="48"
            />
          </ProfileLink>
          <div>
            <WriterName>{post.todoMember.nickname}</WriterName>
            <CreateDate>{post.todo.todoAt}</CreateDate>
          </div>
        </PostHeader>
        <TodoContent>
          <div className="finished">{post.todo.finished ? '❌' : '✅'}</div>
          <div className="todo">{post.todo.content}</div>
          <div>해시태그 자리</div>
        </TodoContent>
        <PersonaReaction
          personas={post.todo.personas}
          todoId={post.todo.todoId}
          setShowMore={setShowMore}
          setPersonaNagList={setPersonaNagList}
        />
        <CommentsContainer>
          <NagCommentItem
            key={post.nag.nagId}
            id={post.nag.nagId}
            like={post.nag.likeCount}
            content={post.nag.content}
            img={post.nag.nagMember.imageUrl}
          />
          {showMore &&
            personaNagList &&
            personaNagList.map((pesonaNag) => {
              return (
                <NagCommentItem
                  key={pesonaNag.personaId}
                  id={pesonaNag.personaId}
                  like={pesonaNag.likeCount}
                  content={pesonaNag.content}
                  img={profileImg} // 캐릭터 이미지
                />
              );
            })}
        </CommentsContainer>
        <ShowMoreButton onClick={handleClickShowMore}>
          {showMore ? <img src={hideImg} /> : <img src={showMoreImg} />}
        </ShowMoreButton>
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
const ShowMoreButton = styled.div`
  cursor: pointer;
  display: flex;
  justify-content: center;
  & > img {
    width: 50px;
  }
`;

const CommentsContainer = styled.div`
  margin-top: 20px;
`;

export default TodoPost;
