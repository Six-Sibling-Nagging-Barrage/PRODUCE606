import React from 'react';
import tw, { styled } from 'twin.macro';
import likeIcon from '../../../assets/like_icon.avif';
import { useQueryClient, useMutation } from '@tanstack/react-query';

const NagCommentItem = (props) => {
  const { isMemberNag, id, isLiked, likeCount, content, writer, nag } = props;

  const queryClient = useQueryClient();
  const updateLike = useMutation(
    (nagId) => {
      // TODO: 잔소리 좋아요 api 호출

      // 임시코드
      console.log(nagId + ' 좋아요 api 호출');
      nag.isLiked = !nag.isLiked;
      nag.likeCount = nag.isLiked ? nag.likeCount + 1 : nag.likeCount - 1;
    },
    {
      onMutate: (nagId) => {
        const prevNag = queryClient.getQueryData(['nagLike', nagId]);
        const newNag = {
          ...nag,
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

  const handleLikeClick = () => {
    updateLike.mutate(id);
  };

  return (
    <CommentContainer>
      <Profile>
        <ProfileImg src={writer.img} />
        <NickName>{writer.nickname}</NickName>
      </Profile>
      <CommentContentWrapper>
        <CommentContent>{content}</CommentContent>
      </CommentContentWrapper>
      {isMemberNag && (
        <LikeButton onClick={handleLikeClick}>
          {nag.isLiked ? (
            <LikeImg
              src={likeIcon}
              filter="invert(44%) sepia(58%) saturate(3914%) hue-rotate(335deg) brightness(100%) contrast(103%);"
            />
          ) : (
            <LikeImg
              src={likeIcon}
              filter="invert(99%) sepia(29%) saturate(0%) hue-rotate(229deg) brightness(112%) contrast(86%);"
            />
          )}
          <div>{nag.likeCount}</div>
        </LikeButton>
      )}
    </CommentContainer>
  );
};

const CommentContainer = styled.div`
  display: flex;
  position: relative;
  & .profile-img {
    width: 50px;
  }
`;

const Profile = styled.div`
  width: 10%;
`;

const NickName = styled.div`
  font-size: 14px;
`;

const ProfileImg = styled.img`
  ${tw`w-8 h-8 rounded-full`}
  margin: 0 auto;
`;

const CommentContentWrapper = styled.div`
  position: relative;
  width: 80%;
  line-height: 18px;
  padding-left: 20px;
  text-align: left;
  padding-right: 30px;
`;

const CommentContent = styled.div`
  position: relative;
  top: 50%;
  transform: translateY(-50%);
`;

const LikeButton = styled.button`
  position: absolute;
  right: 0;
  height: 64px;
  & > img {
    width: 25px;
  }
`;

const LikeImg = styled.img`
  filter: ${(props) => props.filter};
`;

export default NagCommentItem;
