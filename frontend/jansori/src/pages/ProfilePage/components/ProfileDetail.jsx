import React, { useState } from 'react';
import { styled } from 'twin.macro';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import ProfileImg from '../../../components/Profile/ProfileImg';
import editImg from '../../../assets/edit_icon.png';

const ProfileDetail = (props) => {
  const { isMine, profile, nags, tags } = props;

  return (
    <ProfileDetailContainer>
      <Header>
        {isMine && (
          <button>
            <img src={editImg} />
          </button>
        )}
      </Header>
      <ProfileImg profileImg={profile.imageUrl} size="120px" />
      <Nickname>{profile.nickname}</Nickname>
      <Bio>{profile.bio}</Bio>
      <Wrapper>
        <HashTagContainer>
          {tags.map((tag) => {
            return <HashTagItem key={tag.tagId} hashTag={tag} />;
          })}
        </HashTagContainer>
      </Wrapper>
    </ProfileDetailContainer>
  );
};

const ProfileDetailContainer = styled.div`
  text-align: center;
  margin: 0 auto;
`;

const Header = styled.div`
  position: relative;
  width: 100%;
  height: 20px;
  margin: 0 auto;
  margin-top: 10px;
  & > button {
    position: absolute;
    top: 0;
    right: 0;
    width: 20px;
  }
`;

const Nickname = styled.div`
  font-weight: bold;
`;

const Bio = styled.div`
  font-size: 14px;
  width: 70%;
  margin: 0 auto;
`;

const Wrapper = styled.div`
  width: 50%;
  height: 90px;
  margin: 10px auto;
  padding: 10px;
  border-radius: 10px;
  box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2); /* 내부에 그림자 추가 */
`;

const HashTagContainer = styled.div`
  transform: translateY(0%);
  height: 100%;
  display: flex;
  overflow-x: auto;
  flex-wrap: wrap;
  flex-direction: column;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

export default ProfileDetail;
