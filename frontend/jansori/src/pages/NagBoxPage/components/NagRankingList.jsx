import React, { useState } from 'react';
import tw, { styled } from 'twin.macro';
import { useRecoilState } from 'recoil';
import NagRankingItem from './NagRankingItem';
import {
  getNagRanking,
  updateLikeNag,
  updateNagUnlock,
} from '../../../apis/api/nag';
import { ticketState } from '../../../states/user';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import SnackBar from '../../../components/UI/SnackBar';

const NagRankingList = () => {
  const queryClient = useQueryClient();
  const [ticket, setTicket] = useRecoilState(ticketState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const fetchNagRankings = async () => {
    const data = await getNagRanking();
    return data;
  };

  const { data } = useQuery(['nagRankings'], fetchNagRankings);

  const toggleLike = async (nag) => {
    const data = await updateLikeNag(nag.nagId);
    return data;
  };

  const updateLikeMutation = useMutation((nagId) => toggleLike(nagId), {
    onMutate: async (nagId) => {
      await queryClient.cancelQueries(['nagRankings']);
      const prevNagRankings = queryClient.getQueryData(['nagRankings']);
      queryClient.setQueryData(['nagRankings'], (oldData) => {
        const newData = oldData?.nags.map((nagRanking) => {
          console.log(nagRanking.likeCount);
          if (nagRanking.nagId === nagId) {
            return {
              ...nagRanking,
              isLiked: !nagRanking.isLiked,
              likeCount: nagRanking.isLiked
                ? nagRanking.likeCount - 1
                : nagRanking.likeCount + 1,
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

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  const toggleUnlock = async (nag) => {
    if (ticket < 1) {
      setSnackBarMessage(
        '티켓이 부족해 잔소리를 열어볼 수 없어요! 잔소리를 작성하면 티켓을 얻을 수 있어요.'
      );
      return setShowSnackBar(true);
    }

    const data = await updateNagUnlock(nag.nagId);
    if (data.code === '200') {
      setTicket(data.data.ticketCount);
      setSnackBarMessage('티켓 1개를 소모해 잔소리를 열었어요.');
      setShowSnackBar(true);
    }
    return data;
  };

  const updateUnlockMutation = useMutation((nagId) => toggleUnlock(nagId), {
    onMutate: async (nagId) => {
      await queryClient.cancelQueries(['nagRankings']);
      const prevNagRankings = queryClient.getQueryData(['nagRankings']);
      queryClient.setQueryData(['nagRankings'], (oldData) => {
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
          return (
            <NagRankingItem
              key={memberNagRanking.nagId}
              rank={index + 1}
              nag={memberNagRanking}
              toggleLike={updateLikeMutation.mutate}
              toggleUnlock={updateUnlockMutation.mutate}
            />
          );
        })}
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </NagRankingListWrap>
  );
};

export default NagRankingList;

const NagRankingListWrap = styled.div`
  ${tw`
  flex
  flex-col`}
  margin-bottom: 50px;
`;
