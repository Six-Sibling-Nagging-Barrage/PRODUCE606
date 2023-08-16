import React from 'react';
import tw, { styled } from 'twin.macro';
import { useLocation, useNavigate } from 'react-router-dom'; // 이 부분에서 react-router-dom의 Link 컴포넌트를 가져옴
import nagIcon from '../../assets/nag_icon.png';
import { navBarState } from '../../states/user';
import { useSetRecoilState } from 'recoil';

const FloatingButton = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const setNavBar = useSetRecoilState(navBarState);

  const handleFloatingButton = () => {
    setNavBar(-1);
    if (location.pathname === '/nag') window.location.reload();
    navigate('/nag');
  };

  return (
    <FloatingButtonContainer onClick={handleFloatingButton}>
      <img src={nagIcon}></img>
    </FloatingButtonContainer>
  );
};

export default FloatingButton;

const FloatingButtonContainer = styled.div`
  ${tw`fixed bottom-8 right-8 rounded-full flex items-center justify-center text-white text-xl`};
  background-color: rgba(255, 255, 255, 0.7);
  padding: 15px;
  width: 70px;
  height: 70px;
  font-size: 40px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.2);
  cursor: pointer;
`;
