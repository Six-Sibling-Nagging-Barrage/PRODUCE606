import React, { useEffect, useState } from 'react';
import InfiniteScroll from './InfiniteScroll';
import tw, { styled } from 'twin.macro';
import TodoPost from './TodoPost';
import {
  useQueryClient,
  useInfiniteQuery,
  useMutation,
} from '@tanstack/react-query';
import { updateLikeNag, updateNagUnlock } from '../../../apis/api/nag';
import { useRecoilState } from 'recoil';
import { ticketState } from '../../../states/user';
import SnackBar from '../../../components/UI/SnackBar';
import LoadingShimmer from '../../../components/UI/LoadingShimmer';

const Feed = (props) => {
  const {
    specificTag,
    getFeedData,
    currentPostId,
    setCurrentPostId,
    setCurrentPost,
    setPersonaReaction,
  } = props;

  const [ticket, setTicket] = useRecoilState(ticketState);
  const [showSnackBar, setShowSnackBar] = useState(false);
  const [snackBarMessage, setSnackBarMessage] = useState('');

  const queryClient = useQueryClient();

  const pageSize = 10;
  let param;

  if (specificTag === -1) {
    param = { cursor: null, pageSize };
  } else {
    param = { cursor: null, tagId: specificTag, pageSize };
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
    ['feed'],
    ({ pageParam = param }) => fetchMoreTodoPosts(pageParam),
    {
      getNextPageParam: (lastPage) => {
        if (!lastPage?.hasNext) return undefined;
        if (specificTag === -1) {
          return { cursor: lastPage.nextCursor, pageSize };
        } else {
          return { tagId: specificTag, cursor: lastPage.nextCursor, pageSize };
        }
      },
    }
  );

  useEffect(() => {
    window.scrollTo(0, 0);
    if (specificTag === -1) {
      param = { cursor: null, pageSize };
    } else {
      param = { cursor: null, tagId: specificTag, pageSize };
    }
    refetch();
  }, [specificTag, refetch]);

  const fetchMoreTodoPosts = async (pageParam) => {
    const data = await getFeedData(pageParam);
    return data?.data;
  };

  const toggleLike = async (nagId) => {
    // 잔소리 좋아요 api 호출
    const data = await updateLikeNag(nagId);
    return data;
  };

  const updateLikeMutation = useMutation(
    ({ postId, nagId }) => toggleLike(nagId),
    {
      onMutate: async ({ postId, nagId }) => {
        await queryClient.cancelQueries(['feed']);
        const prevFeed = queryClient.getQueryData(['feed']);
        queryClient.setQueryData(['feed'], (oldData) => {
          const newData = oldData?.pages?.map((page) => {
            return {
              ...page,
              feed: page?.feed?.map((post) => {
                if (!post.nag) return post;
                if (post.id === postId) {
                  return {
                    ...post,
                    nag: {
                      ...post.nag,
                      isLiked: !post.nag?.isLiked,
                      likeCount: post.nag?.isLiked
                        ? post.nag?.likeCount - 1
                        : post.nag?.likeCount + 1,
                    },
                  };
                }
                return post;
              }),
            };
          });
        });
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['feed']);
      },
      onError: (err, variables, context) => {
        queryClient.setQueryData(['feed'], context?.prevFeed);
      },
    }
  );

  const handleSnackBarClose = () => {
    setShowSnackBar(false);
    setSnackBarMessage('');
  };

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

  const updateUnlockMutation = useMutation(
    ({ postId, nagId }) => toggleUnlock(nagId),
    {
      onMutate: async ({ postId, nagId }) => {
        await queryClient.cancelQueries(['feed']);
        const prevFeed = queryClient.getQueryData(['feed']);
        queryClient.setQueryData(['feed'], (oldData) => {
          const newData = oldData?.pages?.map((page) => {
            return {
              ...page,
              feed: page?.feed?.map((post) => {
                if (!post.nag) return post;
                if (post.id === postId) {
                  return {
                    ...post,
                    nag: {
                      ...post.nag,
                      unlocked: true,
                    },
                  };
                }
                return post;
              }),
            };
          });
        });
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['feed']);
      },
      onError: (err, variables, context) => {
        queryClient.setQueryData(['feed'], context?.prevFeed);
      },
    }
  );

  return (
    <FeedContainer>
      {isLoading && <LoadingShimmer />}
      <ul>
        {data?.pages?.map((page) =>
          page?.feed?.map((post) => {
            return (
              <TodoPost
                post={post}
                key={post.todoId}
                currentPostId={currentPostId}
                setCurrentPostId={setCurrentPostId}
                setCurrentPost={setCurrentPost}
                setPersonaReaction={setPersonaReaction}
                toggleLike={updateLikeMutation.mutate}
                toggleUnlock={updateUnlockMutation.mutate}
                randomPersona={post.todoId % 6}
              />
            );
          })
        )}
      </ul>
      <InfiniteScroll
        fetchNextPage={fetchNextPage}
        hasNextPage={hasNextPage}
        isFetchingNextPage={isFetchingNextPage}
      />
      {showSnackBar && (
        <SnackBar message={snackBarMessage} onClose={handleSnackBarClose} />
      )}
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  width: 35%;
  @media (min-width: 990px) and (max-width: 1200px) {
    width: 50%;
  }
  @media (min-width: 786px) and (max-width: 990px) {
    width: 60%;
  }
  @media (min-width: 680px) and (max-width: 786px) {
    width: 70%;
  }
  @media (max-width: 680px) {
    width: 90%;
  }
`;

export default Feed;
