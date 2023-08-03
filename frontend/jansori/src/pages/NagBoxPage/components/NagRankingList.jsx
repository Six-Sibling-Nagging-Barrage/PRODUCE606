import React from 'react';
import tw, { styled } from 'twin.macro';
import NagRankingItem from './NagRankingItem';

const NagRankingList = () => {
  const memberNagRankings = [
    {
      nagId: 7,
      unlocked: null,
      content: 'ㄴㅇ ㅈㄱㄱ ㅁㅁㅎㄷ ㄴㄱㄱㄴ ㅋㄷㅎㄷ',
      likeCount: 5,
    },
    {
      nagId: 8,
      unlocked: null,
      content: 'ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ',
      likeCount: 253,
    },
    {
      nagId: 12,
      unlocked: null,
      content: 'ㄴㄷ ㅋㄷㅈㅎㄱ ㅅㄷ',
      likeCount: 152,
    },
    {
      nagId: 21,
      unlocked: null,
      content: 'ㅅㄹㅈㅅㄹㅈㅅㄹㅈ',
      likeCount: 5,
    },
    {
      nagId: 56,
      unlocked: null,
      content: 'ㄴㄷㄹㄴㅇㄹ ㄴㅇㄹ',
      likeCount: 86,
    },
  ];

  return (
    <NagRankingListWrap>
      {memberNagRankings.map((memberNagRanking, index) => {
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

const NagRankingItemWrap = styled.div(({ isOdd }) => [isOdd ? tw`mr-auto` : tw`ml-auto`]);
