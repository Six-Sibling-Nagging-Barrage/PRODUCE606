import React from 'react';
import tw, { styled } from 'twin.macro';
import SpeechBubble from '../../../components/UI/SpeechBubble';

const NagRankingItem = (props) => {
  const { id, likeCount, content, isOdd } = props;

  return (
    <NagRankingItemContainer isOdd={isOdd}>
      <SpeechBubble text={content} isOdd={isOdd} />
      <LikeButton isOdd={isOdd}>{likeCount}</LikeButton>
    </NagRankingItemContainer>
  );
};

export default NagRankingItem;

const NagRankingItemContainer = styled.div`
  ${tw`
my-5
relative
`}
`;

const LikeButton = styled.div(({ isOdd }) => [
  tw`bg-white w-fit absolute z-50 `,
  isOdd ? tw`-bottom-3 -right-3` : tw`-bottom-3 -left-3`,
]);
