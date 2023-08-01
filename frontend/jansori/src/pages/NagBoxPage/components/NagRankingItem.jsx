import React from 'react';
import tw, { styled } from 'twin.macro';
import SpeechBubble from '../../../components/UI/SpeechBubble';

const NagRankingItem = (props) => {
  const { id, likeCount, content, isOdd } = props;

  return (
    <NagRankingItemContainer>
      <SpeechBubble text={id + content} isOdd={isOdd} />
      <LikeButton isOdd={isOdd}>{likeCount}</LikeButton>
    </NagRankingItemContainer>
  );
};

export default NagRankingItem;

const NagRankingItemContainer = styled.div`
  ${tw`w-4/12
  mx-auto
  my-10
  relative
  `}
`;

const LikeButton = styled.div(({ isOdd }) => [
  tw`bg-white w-fit absolute z-50`,
  isOdd ? tw`-bottom-3 -right-3` : tw`-bottom-3 -left-3`,
]);
