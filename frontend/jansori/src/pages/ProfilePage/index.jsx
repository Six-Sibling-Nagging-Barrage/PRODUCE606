import React, { useEffect, useState } from 'react';
import ProfileDetail from './components/ProfileDetail';
import { styled } from 'twin.macro';
import profileImg from '../../assets/profile_img.jpeg';
import TabItem from './components/TabItem';
import NagHistory from './components/NagHistory';
import { getMemberProfile } from '../../apis/api/member';
import TodoHistory from './components/TodoHistory';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../states/user';
import { getFollowTagList } from '../../apis/api/tag';
import { useLocation } from 'react-router-dom';

const tabs = ['TODO', '잔소리'];

const ProfilePage = () => {
  const [currentTab, setCurrentTab] = useState(0);

  const memberId = useRecoilValue(memberIdState);

  const [profile, setProfile] = useState(null);
  const [tags, setTags] = useState([]);
  const [isMine, setIsMine] = useState(false);

  const location = useLocation();
  const query = new URLSearchParams(location.search);

  // URL에서 query parameter 가져오기
  const id = parseInt(query.get('id'));

  useEffect(() => {
    console.log(id, memberId);
    if (id === memberId) setIsMine(true);
    // 유저 프로필 조회 api 호출
    // TODO: 유저가 팔로우한 해시태그 조회 api 호출
    (async () => {
      const profileRes = await getMemberProfile(id);
      setProfile(profileRes.data);
      const tagRes = await getFollowTagList(id);
      setTags(tagRes.tags);
    })();
  }, []);

  useEffect(() => {
    // TODO: 내가 보낸 잔소리 조회 api 호출
  }, [currentTab]);

  return (
    <ProfileContainer>
      {profile && (
        <ProfileDetail
          isMine={isMine}
          profile={profile}
          tags={tags}
          setTags={setTags}
        />
      )}
      <TabContainer>
        <Tabs>
          {tabs.map((tab, index) => (
            <TabItem
              key={index}
              id={index}
              title={tab}
              currentTab={currentTab}
              setCurrentTab={setCurrentTab}
            />
          ))}
          <Glider currentTab={currentTab} />
        </Tabs>
        <TabContent>
          {currentTab === 0 ? (
            <TodoHistory></TodoHistory>
          ) : (
            <NagHistory></NagHistory>
          )}
        </TabContent>
      </TabContainer>
    </ProfileContainer>
  );
};

const ProfileContainer = styled.div`
  padding-top: 72px;
  width: 70%;
  margin: 0 auto;
  margin-bottom: 2vh;
`;

const TabContainer = styled.div`
  margin: 0 auto;
`;

const Tabs = styled.div`
  display: flex;
  position: relative;
  justify-content: center;
  margin-top: 30px;
  & * {
    z-index: 2;
  }
`;

const TabContent = styled.div`
  height: fit-content;
  background-color: #f4efff;
  border-radius: 5px;
  padding: 20px;
`;

const Glider = styled.span`
  position: absolute;
  display: flex;
  height: 40px;
  width: 300px;
  background-color: #f4efff;
  z-index: 1;
  border-radius: 10px 10px 0 0;
  transition: 0.25s ease-out;
  ${({ currentTab }) =>
    currentTab === 0
      ? `transform: translateX(-50%);`
      : `transform: translateX(50%);`}
`;

export default ProfilePage;
