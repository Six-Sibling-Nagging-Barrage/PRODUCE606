import React, { useEffect, useState } from 'react';
import NagItem from './NagItem';
import { styled } from 'twin.macro';
import { MasonryInfiniteGrid } from '@egjs/react-infinitegrid';
import { getMemberNagList, getMyNagList } from '../../../apis/api/nag';
import StartButton from '../../MainPage/components/StartButton';
import { personas } from '../../../constants/persona';
import LoadingShimmer from '../../../components/UI/LoadingShimmer';
import {
  useQueryClient,
  useInfiniteQuery,
  useMutation,
} from '@tanstack/react-query';
import { ticketState } from '../../../states/user';
import { updateLikeNag, updateNagUnlock } from '../../../apis/api/nag';
import { useRecoilState } from 'recoil';
import SnackBar from '../../../components/UI/SnackBar';

const NagHistory = (props) => {
  const { isMine, id } = props;

  const queryClient = useQueryClient();

  const [ticket, setTicket] = useRecoilState(ticketState);

  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const pageSize = 10;
  let param;

  if (isMine) {
    param = { cursor: null, pageSize };
  } else {
    param = { cursor: null, memberId: id, pageSize };
  }

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isError,
    isLoading,
    refetch,
  } = useInfiniteQuery(
    ['nags'],
    ({ pageParam = param }) => fetchMoreNags(pageParam),
    {
      getNextPageParam: (lastPage) => {
        if (!lastPage?.hasNext) return null;
        if (isMine) {
          return { cursor: lastPage.nextCursor, pageSize };
        } else {
          return { memberId: id, cursor: lastPage.nextCursor, pageSize };
        }
      },
    }
  );

  useEffect(() => {
    if (isMine) {
      param = { cursor: null, pageSize };
    } else {
      param = { cursor: null, memberId: id, pageSize };
    }
  }, [isMine]);

  const toggleUnlock = async (nagId) => {
    if (ticket < 1) {
      setSnackBarMessage(
        '티켓이 부족해 잔소리를 열어볼 수 없어요! 잔소리를 작성하면 티켓을 얻을 수 있어요.'
      );
      return setShowSnackBar(true);
    }
    // 잔소리 초성 해제 api
    const data = await updateNagUnlock(nagId);
    if (data.code === '200') {
      setTicket(data.data.ticketCount);
      setSnackBarMessage('티켓 1개를 소모해 잔소리를 열었어요.');
      setShowSnackBar(true);
    }
    return data;
  };

  const toggleLike = async (nagId) => {
    // 잔소리 좋아요 api 호출
    const data = await updateLikeNag(nagId);
    return data;
  };

  const updateLikeMutation = useMutation(({ nagId }) => toggleLike(nagId), {
    onMutate: async ({ nagId }) => {
      await queryClient.cancelQueries(['nags']);
      const prevNags = queryClient.getQueryData(['nags']);
      queryClient.setQueryData(['nags'], (oldData) => {
        const newData = oldData?.pages?.map((page) => {
          return {
            ...page,
            feed: page?.nags?.map((nag) => {
              if (nag.nagId === nagId) {
                return {
                  ...nag,
                  isLiked: !nag.isLiked,
                  likeCount: nag.isLiked
                    ? nag.likeCount - 1
                    : nag.likeCount + 1,
                };
              }
              return nag;
            }),
          };
        });
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['nags']);
    },
    onError: (err, variables, context) => {
      queryClient.setQueryData(['nags'], context?.prevNags);
    },
  });

  const updateUnlockMutation = useMutation(({ nagId }) => toggleUnlock(nagId), {
    onMutate: async ({ nagId }) => {
      await queryClient.cancelQueries(['nags']);
      const prevNags = queryClient.getQueryData(['nags']);
      queryClient.setQueryData(['nags'], (oldData) => {
        const newData = oldData?.pages?.map((page) => {
          return {
            ...page,
            nags: page?.nags?.map((nag) => {
              if (nag.nagId === nagId) {
                return {
                  ...nag,
                  unlocked: true,
                };
              }
              return nag;
            }),
          };
        });
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(['nags']);
    },
    onError: (err, variables, context) => {
      queryClient.setQueryData(['nags'], context?.prevNags);
    },
  });

  const fetchMoreNags = async (pageParam) => {
    let data;
    // 내가 보낸 잔소리 목록 조회 api
    if (isMine) data = await getMyNagList(pageParam);
    // 다른 사람이 보낸 잔소리 목록 조회 api
    else data = await getMemberNagList(pageParam);
    return data?.data;
  };

  const handleFetchMoreNags = () => {
    if (hasNextPage) {
      fetchNextPage();
    }
  };

  const randomIndex = () => {
    return Math.floor(Math.random() * 6);
  };

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

  return (
    <>
      {isLoading ? (
        <LoadingShimmer />
      ) : (
        <>
          {data && data.pages[0]?.nags.length > 0 ? (
            <MasonryInfiniteGrid
              align="center"
              gap={10}
              onRequestAppend={handleFetchMoreNags}
              loading={isFetchingNextPage}
            >
              {data.pages.flatMap((page, index) =>
                page.nags.map((nag, nagIndex) => (
                  <NagItem
                    key={`${index}_${nagIndex}`}
                    isMine={isMine}
                    nag={nag}
                    toggleUnlock={updateUnlockMutation.mutate}
                  />
                ))
              )}
            </MasonryInfiniteGrid>
          ) : (
            <StartButtonWrapper>
              <PersonaImg>
                <img src={personas[randomIndex()].gifUrl} />
              </PersonaImg>
              <Message>아직 잔소리를 보낸 적이 없어요!</Message>
              <StartButton nagCount={-1}></StartButton>
            </StartButtonWrapper>
          )}
        </>
      )}
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </>
  );
};

const PersonaImg = styled.div`
  width: fit-content;
  margin: 0 auto;
  & > img {
    width: 100px;
    height: 100px;
    object-fit: cover;
    border-radius: 50%;
  }
`;

const Message = styled.div`
  padding-top: 10px;
  padding-bottom: 20px;
`;

const StartButtonWrapper = styled.div`
  margin: 20px auto;
`;

export default NagHistory;
