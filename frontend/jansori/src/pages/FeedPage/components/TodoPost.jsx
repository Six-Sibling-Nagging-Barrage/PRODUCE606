import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import profileImg from '../../../assets/profileImg.png';
import showMoreImg from '../../../assets/show_more.png';
import hideImg from '../../../assets/hide.png';
import NagCommentItem from './NagCommentItem';
import HashTagItem from '../../../components/HashTag/HashTagItem';

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
            <ProfileImage src={post.member.imageUrl} width="48" height="48" />
          </ProfileLink>
          <div>
            <WriterName>{post.member.nickname}</WriterName>
            <CreateDate>{post.todoAt}</CreateDate>
          </div>
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
        {/* <PersonaReaction
          personas={post.todo.personas}
          todoId={post.todo.todoId}
          setShowMore={setShowMore}
          setPersonaNagList={setPersonaNagList}
        /> */}
        <CommentsContainer>
          <NagCommentItem
            key={post.nag.nagId}
            isMemberNag={true}
            id={post.nag.nagId}
            like={post.nag.likeCount}
            content={post.nag.content}
            img={post.nag.nagMember.imageUrl}
            nag={post.nag}
          />
          {/* {showMore &&
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
            })} */}
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
const ShowMoreButton = styled.div`
  cursor: pointer;
  display: flex;
  justify-content: center;
  & > img {
    width: 50px;
  }
`;

const CommentsContainer = styled.div``;

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 10px auto;
  display: flex;
`;

export default TodoPost;
