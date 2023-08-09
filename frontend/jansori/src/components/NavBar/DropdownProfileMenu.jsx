import React from 'react';
import { useRecoilValue, useRecoilState, useSetRecoilState } from 'recoil';
import tw, { styled } from 'twin.macro';
import {
  isLoginState,
  profileImgState,
  memberInfoState,
} from '../../states/user';
import { useNavigate } from 'react-router-dom';
import { createLogout } from '../../apis/api/member';

const DropdownProfileMenu = () => {
  const navigate = useNavigate();

  const profileImg = useRecoilValue(profileImgState);
  const member = useRecoilValue(memberInfoState);
  const setIsLogin = useSetRecoilState(isLoginState);

  const accessToken = localStorage.getItem('member_access_token');

  const handleLogOut = async () => {
    // 로그아웃 api 호출
    const data = await createLogout({
      accessToken,
    });
    setIsLogin(false);
    localStorage.removeItem('member_access_token');
    localStorage.removeItem('member_refresh_token');
    localStorage.removeItem('member_token_exp');
    localStorage.removeItem('recoil-persist');

    window.location.replace('/');
  };

  return (
    <DropdownProfileMenuContainer>
      <BackgroundContainer />
      <DropdownMenuContent>
        <ItemContainer>
          <Avatar src={member.imageUrl} alt="profile" />
          <MemberName>{member.nickname}</MemberName>
        </ItemContainer>
        <ItemContainer onClick={handleLogOut}>LOGOUT</ItemContainer>
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
  ${tw`relative z-50 py-4`};
`;

const ItemContainer = styled.div`
  ${tw`flex justify-center items-center p-2`}
  &:hover {
    cursor: pointer;
  }
`;

const Avatar = styled.img`
  ${tw`w-8 h-8 rounded-full border-2 border-violet-300 mr-5`}
`;

const MemberName = styled.span`
  ${tw`text-base`}
`;
