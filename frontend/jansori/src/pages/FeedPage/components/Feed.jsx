import React, { useEffect, useState } from 'react';
import InfiniteScroll from './InfiniteScroll';
import tw, { styled } from 'twin.macro';
import PersonaReaction from './PersonaReaction';
import TodoPost from './TodoPost';
import { useQueryClient } from '@tanstack/react-query';
import { useInfiniteQuery, useMutation } from '@tanstack/react-query';
import { updateLikeNag } from '../../../apis/api/nag';

const Feed = (props) => {
  const { specificTag, hasFollowingHashTags, getFeedData } = props;

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

  const fetchMoreTodoPosts = async (pageParam) => {
    if (!hasFollowingHashTags) return undefined;
    const data = await getFeedData(pageParam);
    return data;
  };

  const toggleLike = async (nagId) => {
    // 잔소리 좋아요 api 호출
    const data = updateLikeNag(nagId);
    return data;
  };

  const {
    data,
    fetchNextPage,
    hasNextPage,
    isFetchingNextPage,
    isError,
    isLoading,
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

  const updateLikeMutation = useMutation(
    ({ postId, nagId }) => toggleLike(nagId),
    {
      onMutate: async ({ postId, nagId }) => {
        await queryClient.cancelQueries(['feed']);
        const prevFeed = queryClient.getQueryData(['feed']);
        queryClient.setQueryData(['feed'], (oldData) => {
          const newData = oldData.pages.map((page) => {
            return {
              ...page,
              feed: page.feed.map((post) => {
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
      {hasFollowingHashTags ? (
        <>
          <ul>
            {data?.pages.map((page) =>
              page.feed.map((post) => {
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
        </>
      ) : (
        <div>해시태그 없음</div>
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
