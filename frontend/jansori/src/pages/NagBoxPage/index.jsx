import React, { useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import NagRankingList from './components/NagRankingList';
import FeedBackground from '../../components/UI/FeedBackground';
import RollingBanner from './components/RollingBanner';
// import FeedBackground from '../../components/UI/FeedBackground';

const NagBoxPage = () => {
  useEffect(() => {
    const role = JSON.parse(
      localStorage.getItem('recoil-persist')
    ).memberRoleState;
    if (role === 'GUEST') {
      window.location.href = '/initialprofile';
    }
  }, []);

  return (
    <>
      <FeedBackground />
      <RollingBanner />
      {/* 잔소리 랭킹 */}
      <NagRannkingContainer>
        <NagTitle>🏆 현재 잔소리 TOP 5 🏆</NagTitle>
        <NagRankingList />
      </NagRannkingContainer>
    </>
  );
};

export default NagBoxPage;

const NagRannkingContainer = styled.div`
  ${tw`
  w-4/5
  md:w-1/2
  mx-auto`}
`;

const NagTitle = styled.p`
  font-family: 'PyeongChangPeace-Bold';
  ${tw`font-bold text-2xl`}
  margin-bottom: 10px;
`;
