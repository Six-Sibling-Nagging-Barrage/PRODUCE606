import React from 'react';
import { useRecoilValue, useRecoilState } from 'recoil';
import tw, { styled } from 'twin.macro';
import { isLogin, profileImgData, memberNameData } from '../../states/user';
import { useNavigate } from 'react-router-dom';

const DropdownProfileMenu = () => {
  const navigate = useNavigate();

  const [user, setUser] = useRecoilState(isLogin);
  const profileImg = useRecoilValue(profileImgData);
  const memberName = useRecoilValue(memberNameData);

  const handleLogOut = () => {
    setUser(false);
    localStorage.removeItem('user');
    window.location.replace('/');
  };

  return <DropdownProfileMenuContainer>DropdownProfileMenu</DropdownProfileMenuContainer>;
};

export default DropdownProfileMenu;

const DropdownProfileMenuContainer = styled.div`
  ${tw`z-50 
  absolute 
  right-3 
  mt-2 
  w-2/12 h-20 bg-red-500 rounded flex items-center justify-center text-white text-xl`};
  top: calc(100% + 10px); /* Adjust the value as needed to control the distance */
`;
