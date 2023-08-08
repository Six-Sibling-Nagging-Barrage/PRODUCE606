import React, { useState } from 'react';
import { useRecoilValue } from 'recoil';
import tw, { styled } from 'twin.macro';
import { RxHamburgerMenu } from 'react-icons/rx';
import { AiFillBell } from 'react-icons/ai';
import { TbTicket } from 'react-icons/tb';
import Modal from '../UI/Modal';
import { Link } from 'react-router-dom';

import {
  isLoginState,
  profileImgState,
  memberNameState,
} from '../../states/user';
import DropdownProfileMenu from './DropdownProfileMenu';

const NavBar = () => {
  const [isToggleOpen, setIsToggleOpen] = useState(false);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const [isProfileModalOpen, setIsProfileModalOpen] = useState(false);

  const user = useRecoilValue(isLoginState);
  const profileImg = useRecoilValue(profileImgState);

  const handleMenuClick = () => {
    setIsToggleOpen(!isToggleOpen);
  };

  const handleProfileClick = () => {
    setIsProfileModalOpen(!isProfileModalOpen);
  };

  return (
    <>
      <Nav>
        {/* 로고 들어가는 부분 시작 */}
        <NavWrap>
          <Logo href="/" onClick={() => setIsToggleOpen(false)}>
            <img
              className="h-4"
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyy01YZvIVHu61Ocu6oepgHZwHOzzoYHRn8g&usqp=CAU"
              alt="logo"
            />
            <LogoText>육남매 잔소리</LogoText>
          </Logo>
          {/* 오른쪽 로그인 버튼 부분 시작*/}
          <RightButtons>
            {user ? (
              <AfterLoginWrap>
                <button>
                  <AiFillBell style={{ marginRight: '8px' }} />
                </button>
                <TicketWrap>
                  <TicketItem>
                    <TicketItemLogo>
                      <TbTicket />
                    </TicketItemLogo>
                  </TicketItem>
                  <TicketItem>137</TicketItem>
                </TicketWrap>

                <ul>
                  <li>
                    <button onClick={handleProfileClick}>
                      <Avatar src={profileImg} alt="profile" />
                    </button>
                  </li>

                  {isProfileModalOpen && (
                    <li>
                      <DropdownProfileMenu />
                    </li>
                  )}
                </ul>
              </AfterLoginWrap>
            ) : (
              <>
                <NavItemsUl>
                  <li>
                    <NavItem to="/login">로그인</NavItem>
                  </li>
                  <li>
                    <NavItem to="/signup">회원가입</NavItem>
                  </li>
                </NavItemsUl>
              </>
            )}
            {/* 화면 작아졌을 때 햄버거 icon 시작 */}
            <HamburgerButton
              type="button"
              aria-controls="navbar-sticky"
              aria-expanded={isToggleOpen}
              onClick={handleMenuClick}
            >
              <span className="sr-only">Open</span>
              <RxHamburgerMenu className="w-5 h-5" aria-hidden="true" />
            </HamburgerButton>
            {/* 화면 작아졌을 때 햄버거 icon 끝 */}
          </RightButtons>
          {/* 오른쪽 로그인 버튼 부분 끝*/}
          {/* 네비게이션 리스트 부분 시작 */}
          <NavItems id="navbar-sticky" isToggleOpen={isToggleOpen}>
            <NavItemsUl>
              <li>
                <NavItem to="/feed" onClick={() => setIsToggleOpen(false)}>
                  Feed
                </NavItem>
              </li>
              <li>
                <NavItem to="/profile" onClick={() => setIsToggleOpen(false)}>
                  Profile
                </NavItem>
              </li>
              <li>
                <NavItem to="/nagbox" onClick={() => setIsToggleOpen(false)}>
                  잔소리함
                </NavItem>
              </li>
              <li>
                <NavItem
                  to="characterinfo"
                  onClick={() => setIsToggleOpen(false)}
                >
                  About Us
                </NavItem>
              </li>
            </NavItemsUl>
          </NavItems>
          {/* 네비게이션 리스트 부분 끝 */}
        </NavWrap>
        {/* 로고 들어가는 부분 끝 */}
      </Nav>
    </>
  );
};

export default NavBar;

const Nav = styled.nav`
  ${tw`
  fixed
  w-full
  z-20
  top-0
  left-0
  `}
  backdrop-filter:  blur(5px);
  padding: 0 10px;
`;

const NavWrap = styled.div`
  ${tw`
  max-w-screen-2xl
flex
flex-wrap
items-center
justify-between
mx-auto
p-4`}
`;

const Logo = styled.a`
  ${tw`
  flex
  items-center
  `}
`;

const LogoText = styled.span`
  ${tw`
self-center
text-2xl
font-semibold
whitespace-nowrap
ml-3`}
`;

const RightButtons = styled.div`
  ${tw`flex
md:order-2`}
`;

const HamburgerButton = styled.button`
  ${tw`
    inline-flex
    items-center
    p-2
    w-10
    h-10
    ml-3
    justify-center
    text-sm
    text-gray-500
    rounded-lg
    md:hidden
    hover:bg-gray-100
  `}
`;

const LoginButton = styled.button`
  ${tw`text-base
text-blue-600`}
`;

const NavItems = styled.div(({ isToggleOpen }) => [
  tw`
    items-center
    justify-between
    w-full
    hidden
    md:flex
    md:w-auto
    md:order-1`,
  isToggleOpen && tw`flex`,
]);

const NavItemsUl = styled.ul`
  ${tw`
flex
flex-col
p-4
md:p-0
mt-4
font-medium
border
border-gray-100
rounded-lg
w-full
md:flex-row
md:space-x-8
md:mt-0
md:border-0
`}
`;

const NavItem = styled(Link)`
  ${tw`
block
py-2
pl-3
pr-4
text-gray-900
rounded
md:hover:bg-transparent
md:hover:text-blue-700
md:p-0
`}
`;

const LoginTitle = styled.div`
  font-size: 18px;
  margin-bottom: 20px;
`;

const AfterLoginWrap = styled.div`
  ${tw`flex`}
`;

const Avatar = styled.img`
  ${tw`w-8 h-8 ml-3 mr-3 rounded-full border-2 border-violet-200`}
`;

const TicketWrap = styled.div`
  ${tw`flex items-center m-1`}
`;

const TicketItem = styled.div`
  ${tw`flex items-center text-purple-600 mr-2`}
`;

const TicketItemLogo = styled.div`
  transform: rotate(-30deg);
`;
