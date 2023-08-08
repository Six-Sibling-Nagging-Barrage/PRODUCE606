import React from 'react';
import { styled } from 'twin.macro';
import HashTag from '../../../components/HashTag/HashTag';
import ProfileImg from '../../../components/Profile/ProfileImg';
import editImg from '../../../assets/edit_icon.png';
import Slider from 'react-slick';

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
      <HashTagContainer>
        <HashTag editable={false} hashTagList={tags} />
      </HashTagContainer>
      <Bio>{profile.bio}</Bio>
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

const HashTagContainer = styled.div`
  width: fit-content;
  margin: 0 auto;
`;

export default ProfileDetail;
