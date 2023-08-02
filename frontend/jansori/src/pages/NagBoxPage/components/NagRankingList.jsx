import React from 'react';
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
    <div>
      {memberNagRankings.map((memberNagRanking, index) => {
        return (
          <NagRankingItem
            key={memberNagRanking.nagId}
            id={memberNagRanking.nagId}
            content={memberNagRanking.content}
            likeCount={memberNagRanking.likeCount}
            isOdd={(index + 1) % 2 !== 0}
          />
        );
      })}
    </div>
  );
};

export default NagRankingList;
