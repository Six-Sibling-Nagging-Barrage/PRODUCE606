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
import { altImageUrl } from '../../constants/image';

const DropdownProfileMenu = () => {
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

  const handleImgError = (e) => {
    e.target.src = altImageUrl;
  };

  return (
    <DropdownProfileMenuContainer>
      <BackgroundContainer />
      <DropdownMenuContent>
        <ItemContainer>
          <Avatar src={member.imageUrl} onError={handleImgError} />
          <MemberName>{member.nickname}</MemberName>
        </ItemContainer>
        <ItemContainer onClick={handleLogOut}>
          <Logout>LOGOUT</Logout>
        </ItemContainer>
      </DropdownMenuContent>
    </DropdownProfileMenuContainer>
  );
};

export default DropdownProfileMenu;

const DropdownProfileMenuContainer = styled.div`
  ${tw`
  absolute 
  right-1 
  mt-3 
  w-40`};
`;

const BackgroundContainer = styled.div`
  ${tw`
  z-40
  bg-white
  absolute
  w-full
  h-full`}
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const DropdownMenuContent = styled.div`
  ${tw`relative z-50 pt-1`};
`;

const ItemContainer = styled.div`
  ${tw`flex justify-center items-center m-4`}
`;

const Avatar = styled.img`
  ${tw`w-8 h-8 rounded-full mr-2`}
`;

const MemberName = styled.span`
  ${tw`text-base`}
`;

const Logout = styled.div`
  width: fit-content;
  padding: 5px 15px;
  border-radius: 20px;
  &:hover {
    cursor: pointer;
    background-color: rgb(220, 220, 220);
  }
`;
