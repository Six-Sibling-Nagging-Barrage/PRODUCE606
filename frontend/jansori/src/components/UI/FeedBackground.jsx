import { styled } from 'twin.macro';

const FeedBackground = () => {
  return <Background></Background>;
};

const Background = styled.div`
  z-index: -1;
  background-color: #f4f4fa;
  position: fixed; // 배경 이미지가 아닌 배경 레이어를 고정
  top: 0;
  left: 0; // 배경 레이어의 크기는 컨테이너의 크기와 동일하게 처리
  width: 100%;
  height: 100%;
  transform: translateZ(0); // fixed된 요소의 잔상 이슈 해결
  will-change: transform; // 예상되는 변화에 관한 힌트를 브라우저에 제공하여 실제 요소가 변화되기 전에 미리 브라우저는 적절하게 최적화.
`;

export default FeedBackground;
