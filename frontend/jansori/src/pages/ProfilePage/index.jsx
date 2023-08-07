import React, { useEffect, useState } from 'react';
import ProfileDetail from './components/ProfileDetail';
import { styled } from 'twin.macro';
import profileImg from '../../assets/profile_img.jpeg';
import TabItem from './components/TabItem';
import NagHistory from './components/NagHistory';
import { getMyProfile } from '../../apis/api/member';
import { addTokenToHeaders } from '../../apis/utils/authInstance';
import { useRecoilValue } from 'recoil';
import { memberToken } from '../../states/user';

const tabs = ['TODO', '잔소리'];

const ProfilePage = () => {
  const [currentTab, setCurrentTab] = useState(0);

  const jwtToken = useRecoilValue(memberToken);

  addTokenToHeaders(jwtToken);

  useEffect(() => {
    // TODO: 유저 프로필 조회 api 호출
    // TODO: 내가 보낸 잔소리 조회 api 호출
    // TODO: 유저가 팔로우한 해시태그 조회 api 호출
    (async () => {
      const data = await getMyProfile();
    })();
  }, [currentTab]);

  return (
    <ProfileContainer>
      <ProfileDetail
        profile={dummyData.profile}
        nags={dummyData.nags}
        tags={dummyData.tags}
      />
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
            // <TodoHistory></TodoHistory>
            <div></div>
          ) : (
            <NagHistory nags={dummyData.nags}></NagHistory>
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

const dummyData = {
  profile: {
    id: 4,
    nickname: 'Admin001',
    bio: '훌쩍 커버렸어 함께한 기억처럼 널 보는 내 마음은 어느새 여름 지나 가을 기다렸지 all this time Do you want somebody Like I want somebody? 날 보고 웃었지만 Do you think about me now? yeah All the time, yeah, all the time',
    imageUrl: profileImg,
    ticket: 8,
  },
  todos: {},
  tags: [
    {
      tagId: 1,
      tagName: '개발',
    },
    {
      tagId: 2,
      tagName: '코딩',
    },
    {
      tagId: 3,
      tagName: '운동',
    },
    {
      tagId: 4,
      tagName: '일상',
    },
    {
      tagId: 5,
      tagName: '알고리즘',
    },
  ],
};
