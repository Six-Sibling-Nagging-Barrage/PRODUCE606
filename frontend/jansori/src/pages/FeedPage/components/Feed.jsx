import React, { useEffect, useState } from 'react';
import InfiniteScroll from './InfiniteScroll';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import TodoPost from './TodoPost';
import { useQueryClient } from '@tanstack/react-query';
import { useInfiniteQuery, useMutation } from '@tanstack/react-query';
import { updateLikeNag } from '../../../apis/api/nag';

const Feed = (props) => {
  const { specificTag, getFeedData } = props;

  const [currentPostId, setCurrentPostId] = useState(-1);
  const [personaReaction, setPersonaReaction] = useState([]);

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
    console.log(specificTag);
    if (specificTag === -1) {
      param = { cursor: null, pageSize };
    } else {
      param = { cursor: null, tagId: specificTag, pageSize };
    }
    refetch();
  }, [specificTag, refetch]);

  const fetchMoreTodoPosts = async (pageParam) => {
    const data = await getFeedData(pageParam);
    console.log(data.data);
    return data.data;
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
                if (post.id === postId) {
                  return {
                    ...post,
                    isLiked: !post.nag.isLiked,
                    likeCount: post.nag.isLiked
                      ? post.nag.likeCount - 1
                      : post.nag.likeCount + 1,
                  };
                }
                return post;
              }),
            };
          });
          return { ...oldData, pages: newData };
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
      {isLoading && <div>피드 불러 오는 중.......</div>}
      <ul>
        {data?.pages?.map((page) =>
          page?.feed?.map((post) => {
            return (
              <TodoPost
                post={post}
                key={post.todoId}
                currentPostId={currentPostId}
                setCurrentPostId={setCurrentPostId}
                setPersonaReaction={setPersonaReaction}
                toggleLike={updateLikeMutation.mutate}
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
      {currentPostId > -1 && (
        <PersonaReaction
          personaReaction={personaReaction}
          setPersonaReaction={setPersonaReaction}
          setCurrentPostId={setCurrentPostId}
          currentPostId={currentPostId}
        />
      )}
    </FeedContainer>
  );
};

const FeedContainer = styled.div`
  ${tw`w-2/5`}
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
