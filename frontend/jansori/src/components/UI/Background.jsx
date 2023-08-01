import tw, { styled } from 'twin.macro';

const Background = (props) => {
  const { children } = props;
  return <BackgroundStyled>{children}</BackgroundStyled>;
};

const BackgroundStyled = styled.div`
  ${tw`
    min-h-screen
    snap-x
  `}
  z-index: -1;
  background: linear-gradient(
    -45deg,
    rgba(36, 45, 66, 0.15),
    rgba(173, 0, 255, 0.35),
    rgba(113, 84, 255, 0.4),
    rgba(0, 148, 255, 0.4),
    rgba(136, 118, 247, 0.4)
  );
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  @keyframes gradient {
    0% {
      background-position: 0% 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0% 50%;
    }
  }
`;

export default Background;
