import React from 'react';
import tw, { styled } from 'twin.macro';
import { useLocation, useNavigate } from 'react-router-dom'; // 이 부분에서 react-router-dom의 Link 컴포넌트를 가져옴
import nagIcon from '../../assets/nag_icon.png';

const FloatingButton = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleFloatingButton = () => {
    if (location.pathname === '/nag') window.location.reload();
    navigate('/nag');
  };

  return (
    <FloatingButtonContainer onClick={handleFloatingButton}>
      <img src={nagIcon} />
    </FloatingButtonContainer>
  );
};

export default FloatingButton;

const FloatingButtonContainer = styled.div`
  ${tw`fixed bottom-10 right-10 w-16 h-16 rounded-full flex items-center justify-center text-white text-xl`};
  background-color: rgba(255, 255, 255, 0.5);
  padding: 15px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  cursor: pointer;
  & img {
    filter: invert(30%) sepia(0%) saturate(0%) hue-rotate(50deg) brightness(104%) contrast(86%);
  }
`;
