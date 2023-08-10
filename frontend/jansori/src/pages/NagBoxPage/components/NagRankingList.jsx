import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import NagRankingItem from './NagRankingItem';
import { getNagRanking, updateLikeNag } from '../../../apis/api/nag';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';

const NagRankingList = () => {
  const queryClient = useQueryClient();

  const fetchNagRankings = async () => {
    const data = await getNagRanking();
    return data;
  };

  const { data } = useQuery(['nagRankings'], fetchNagRankings);

  const toggleLike = async (nagId) => {
    const data = await updateLikeNag(nagId);
    return data;
  };

  const updateLikeMutation = useMutation((nagId) => toggleLike(nagId), {
    onMutate: async (nagId) => {
      await queryClient.cancelQueries(['nagRankings']);
      const prevNagRankings = queryClient.getQueryData(['nagRankings']);
      queryClient.setQueryData(['nagRankings'], (oldData) => {
        console.log(oldData);
        const newData = oldData?.nags.map((nagRanking) => {
          if (nagRanking.nagId === nagId) {
            return {
              ...nagRanking,
              likeCount: nagRanking.likeCount + 1,
            };
          }
          return nagRanking;
        });
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['nagRankings']);
    },
  });

  return (
    <NagRankingListWrap>
      {Array.isArray(data?.nags) &&
        data?.nags.map((memberNagRanking, index) => {
          const isOdd = (index + 1) % 2 !== 0;
          return (
            <NagRankingItemWrap isOdd={isOdd} key={memberNagRanking.nagId}>
              <NagRankingItem
                id={memberNagRanking.nagId}
                content={memberNagRanking.content}
                likeCount={memberNagRanking.likeCount}
                isOdd={isOdd}
                updateLikeMutation={updateLikeMutation.mutate}
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

const NagRankingItemWrap = styled.div(
  ({ isOdd }) => [isOdd ? tw`mr-auto` : tw`ml-auto`],
  tw`w-3/5
  my-2`
);
