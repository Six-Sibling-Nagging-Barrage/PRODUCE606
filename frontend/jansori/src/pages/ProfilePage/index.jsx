import React, { useEffect, useState } from 'react';
import ProfileDetail from './components/ProfileDetail';
import { styled } from 'twin.macro';
import TabItem from './components/TabItem';
import NagHistory from './components/NagHistory';
import TodoHistory from './components/TodoHistory';
import { useRecoilValue } from 'recoil';
import { memberIdState } from '../../states/user';
import { useLocation } from 'react-router-dom';
import FeedBackground from '../../components/UI/FeedBackground';

const tabs = ['TODO', '잔소리'];

const ProfilePage = () => {
  const [currentTab, setCurrentTab] = useState(0);

  const memberId = useRecoilValue(memberIdState);

  const [isMine, setIsMine] = useState(false);
  const [currentId, setCurrentId] = useState(null);

  const location = useLocation();
  const query = new URLSearchParams(location.search);

  // URL에서 query parameter 가져오기
  const id = parseInt(query.get('id'));

  useEffect(() => {
    const role = JSON.parse(
      localStorage.getItem('recoil-persist')
    ).memberRoleState;
    if (role === 'GUEST') {
      window.location.href = '/initialprofile';
    }
  }, []);

  useEffect(() => {
    window.scrollTo(0, 0);
    setCurrentId(id);
    if (id === memberId) setIsMine(true);
    else setIsMine(false);
  }, [id]);

  return (
    <ProfileContainer>
      <FeedBackground />
      <ProfileDetail isMine={isMine} id={id} />
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
        {currentId && (
          <TabContent>
            {currentTab === 0 ? (
              <TodoHistory isMine={isMine} id={currentId}></TodoHistory>
            ) : (
              <NagHistory isMine={isMine} id={currentId}></NagHistory>
            )}
          </TabContent>
        )}
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
  background-color: #ede7f6;
  border-radius: 5px;
  padding: 20px;
  min-height: 250px;
`;

const Glider = styled.span`
  position: absolute;
  display: flex;
  height: 40px;
  width: 300px;
  background-color: #ede7f6;
  z-index: 1;
  border-radius: 10px 10px 0 0;
  transition: 0.25s ease-out;
  ${({ currentTab }) =>
    currentTab === 0
      ? `transform: translateX(-50%);`
      : `transform: translateX(50%);`}
`;

export default ProfilePage;
