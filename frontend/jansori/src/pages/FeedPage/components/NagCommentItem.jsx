import React from 'react';
import tw, { styled } from 'twin.macro';
import likeIcon from '../../../assets/like_icon.avif';

const NagCommentItem = (props) => {
  const { isMemberNag, todoId, nag, toggleLike } = props;

  const handleLikeClick = () => {
    toggleLike({ todoId, nagId: nag.nagId });
  };

  return (
    <CommentContainer>
      <Profile>
        <ProfileImg src={nag.nagMember.img} />
        <NickName>{nag.nagMember.nickname}</NickName>
      </Profile>
      <CommentContentWrapper>
        <CommentContent>{nag.content}</CommentContent>
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
