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

  return (
    <DropdownProfileMenuContainer>
      <BackgroundContainer />
      <DropdownMenuContent>
        <ItemContainer>
          <Avatar src={profileImg} alt='profile' />
          <MemberName>{memberName}</MemberName>
        </ItemContainer>
        <ItemContainer>mypage</ItemContainer>
        <ItemContainer>LOGOUT</ItemContainer>
      </DropdownMenuContent>
    </DropdownProfileMenuContainer>
  );
};

export default DropdownProfileMenu;

const DropdownProfileMenuContainer = styled.div`
  ${tw`
  absolute 
  right-10 
  mt-3 
  w-48`};
`;

const BackgroundContainer = styled.div`
  ${tw`
  z-40
  bg-white
  opacity-50
  drop-shadow-lg
  rounded-lg
  absolute
  w-full
  h-full`}
`;

const DropdownMenuContent = styled.div`
  ${tw`relative
  z-50`};
`;

const ItemContainer = styled.div`
  ${tw`flex justify-center items-center p-2`}
`;

const Avatar = styled.img`
  ${tw`w-8 h-8 rounded-full border-2 border-violet-300 mr-5`}
`;

const MemberName = styled.span`
  ${tw`text-base`}
`;
