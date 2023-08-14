import React, { useEffect, useState } from 'react';
import { styled } from 'twin.macro';
import HashTagItem from '../../../components/HashTag/HashTagItem';
import ProfileImg from '../../../components/Profile/ProfileImg';
import editImg from '../../../assets/edit_icon.png';
import Modal from '../../../components/UI/Modal';
import ProfileForm from '../../../components/Profile/ProfileForm';
import { getMemberProfile } from '../../../apis/api/member';
import { getFollowTagList } from '../../../apis/api/tag';

const ProfileDetail = (props) => {
  const { isMine, id } = props;

  const [isEditing, setIsEditing] = useState(false);
  const [isEdited, setIsEdited] = useState(false);
  const [profile, setProfile] = useState(null);
  const [tags, setTags] = useState([]);

  useEffect(() => {
    // 유저 프로필 조회 api 호출
    // 유저가 팔로우한 해시태그 조회 api 호출
    (async () => {
      const profileRes = await getMemberProfile(id);
      setProfile(profileRes?.data);
      const tagRes = await getFollowTagList(id);
      setTags(tagRes?.tags);
    })();
  }, [id, isEdited]);

  const handleEditProfile = () => {
    setIsEditing(true);
  };

  return (
    <>
      {isEditing && (
        <Modal setIsModalOpen={setIsEditing}>
          <ProfileForm
            tags={tags}
            prevNickname={profile.nickname}
            prevBio={profile.bio}
            setIsEditing={setIsEditing}
            setIsEdited={setIsEdited}
          />
        </Modal>
      )}
      {profile && (
        <ProfileDetailContainer>
          <Header>
            {isMine && (
              <button onClick={handleEditProfile}>
                <img src={editImg} />
              </button>
            )}
          </Header>
          <ProfileImg profileImg={profile.imageUrl} size="120px" />
          <Nickname>{profile.nickname}</Nickname>
          <Bio>{profile.bio}</Bio>
          <Wrapper>
            <HashTagContainer>
              {tags?.map((tag) => {
                return <HashTagItem key={tag.tagId} hashTag={tag} />;
              })}
            </HashTagContainer>
          </Wrapper>
        </ProfileDetailContainer>
      )}
    </>
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
  font-size: 18px;
`;

const Bio = styled.div`
  width: 50%;
  margin: 10px auto;
`;

const Wrapper = styled.div`
  width: fit-content;
  max-width: 70%;
  max-height: 90px;
  margin: 10px auto;
  padding: 10px;
  border-radius: 10px;
`;

const HashTagContainer = styled.div`
  transform: translateY(0%);
  height: 100%;
  display: flex;
  overflow: auto;
  flex-wrap: wrap;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

export default ProfileDetail;
