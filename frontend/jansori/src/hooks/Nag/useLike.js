import { useQueryClient, useMutation } from '@tanstack/react-query';

const useLike = (nag) => {
  const queryClient = useQueryClient();

  return useMutation(
    (nagId) => {
      // 잔소리 좋아요 api 호출
      // /nags/{nagId}/like
      console.log(nagId + ' 좋아요 api 호출');
    },
    {
      onMutate: (nagId) => {
        const prevNag = queryClient.getQueryData(['nagLike', nagId]);
        const newNag = {
          nagId: nagId,
          isLiked: !nag.isLiked,
          likeCount: nag.isLiked ? nag.likeCount - 1 : nag.likeCount + 1,
        };
        queryClient.setQueryData(['nagLike', nagId], newNag);
        return { prevNag };
      },
      onError: (err, nagId, context) => {
        queryClient.setQueryData(['nagLike', nagId], context?.prevNag);
      },
      onSettled: () => {
        queryClient.invalidateQueries(['nagLike']);
      },
    }
  );
};

export default useLike;
