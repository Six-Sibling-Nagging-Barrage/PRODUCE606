import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import NagRankingItem from './NagRankingItem';
import { getNagRanking } from '../../../apis/api/nag';

const NagRankingList = () => {
  const [memberNagRankings, setMemberNagRankings] = useState([]);

  useEffect(() => {
    (async () => {
      const data = await getNagRanking();
      setMemberNagRankings(data.nags);
    })();
  }, []);

  return (
    <NagRankingListWrap>
      {memberNagRankings?.map((memberNagRanking, index) => {
        const isOdd = (index + 1) % 2 !== 0;
        return (
          <NagRankingItemWrap isOdd={isOdd}>
            <NagRankingItem
              key={memberNagRanking.nagId}
              id={memberNagRanking.nagId}
              content={memberNagRanking.content}
              likeCount={memberNagRanking.likeCount}
              isOdd={isOdd}
            />
          </NagRankingItemWrap>
        );
      })}
    </NagRankingListWrap>
  );
};

export default NagRankingList;

const NagRankingListWrap = styled.div`
  ${tw`
  flex
  flex-col`}
`;

const NagRankingItemWrap = styled.div(({ isOdd }) => [
  isOdd ? tw`mr-auto` : tw`ml-auto`,
]);
