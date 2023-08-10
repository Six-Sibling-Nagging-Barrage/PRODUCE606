import React from 'react';
import tw, { styled } from 'twin.macro';
import { FaThumbsUp } from 'react-icons/fa';
import SpeechBubble from '../../../components/UI/SpeechBubble';

const NagRankingItem = (props) => {
  const { id, likeCount, content, isOdd, updateLikeMutation } = props;

  const handleLikeClick = () => {
    updateLikeMutation(id);
  };

  return (
    <NagRankingItemContainer isOdd={isOdd}>
      <SpeechBubble text={content} isOdd={isOdd} />
      <LikeButton isOdd={isOdd} onClick={handleLikeClick}>
        <FaThumbsUp /> {likeCount}
        {likeCount}
      </LikeButton>
    </NagRankingItemContainer>
  );
};

export default NagRankingItem;

const NagRankingItemContainer = styled.button`
  ${tw`
my-5
relative
`}
`;

const LikeButton = styled.div(({ isOdd }) => [
  tw`bg-white w-fit absolute z-50 `,
  isOdd ? tw`-bottom-3 -right-3` : tw`-bottom-3 -left-3`,
]);
