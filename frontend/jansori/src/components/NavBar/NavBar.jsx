import React, { useState } from 'react';
import styled from 'styled-components';
import tw from 'twin.macro';
import { BiSearch } from 'react-icons/bi';
import { RxHamburgerMenu } from 'react-icons/rx';
import Modal from '../UI/Modal';
import GoogleLoginButton from '../Login/GoogleLoginButton';

// 설치해야 하는 파일
//npm install react-icons --save

const NavBar = () => {
  const [isToggleOpen, setIsToggleOpen] = useState(false);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);

  const handleMenuClick = () => {
    setIsToggleOpen(!isToggleOpen);
  };

  const handleLoginClick = () => {
    setIsLoginModalOpen(true);
  };

  return (
    <>
      <Nav>
        {/* 로고 들어가는 부분 시작 */}
        <NavWrap>
          <Logo href='/'>
            <img
              className='h-4'
              src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyy01YZvIVHu61Ocu6oepgHZwHOzzoYHRn8g&usqp=CAU'
              alt='logo'
            />
            <LogoText>육남매 잔소리</LogoText>
          </Logo>
          {/* 오른쪽 로그인 버튼 부분 시작*/}
          <RightButtons>
            <a href='search'>
              <BiSearch className='mr-6 mt-3' />
            </a>
            <LoginButton onClick={handleLoginClick}>Login</LoginButton>
            {/* 화면 작아졌을 때 햄버거 icon 시작 */}
            <HamburgerButton
              type='button'
              aria-controls='navbar-sticky'
              aria-expanded={isToggleOpen}
              onClick={handleMenuClick}
            >
              <span className='sr-only'>Open</span>
              <RxHamburgerMenu className='w-5 h-5' aria-hidden='true' />
            </HamburgerButton>
            {/* 화면 작아졌을 때 햄버거 icon 끝 */}
          </RightButtons>
          {/* 오른쪽 로그인 버튼 부분 끝*/}
          {/* 네비게이션 리스트 부분 시작 */}
          <NavItems id='navbar-sticky'>
            <NavItemsUl>
              <li>
                <NavItem href='feed'>Feed</NavItem>
              </li>
              <li>
                <NavItem href='todo'>ToDo</NavItem>
              </li>
              <li>
                <NavItem href='nag'>잔소리함</NavItem>
              </li>
              <li>
                <NavItem href='aboutus'>About Us</NavItem>
              </li>
            </NavItemsUl>
          </NavItems>
          {/* 네비게이션 리스트 부분 끝 */}
        </NavWrap>
        {/* 로고 들어가는 부분 끝 */}
      </Nav>
      {isLoginModalOpen && (
        <Modal setIsModalOpen={setIsLoginModalOpen}>
          <div>
            <GoogleLoginButton />
          </div>
        </Modal>
      )}
    </>
  );
};

export default NavBar;

const Nav = styled.nav`
  ${tw`
  bg-white
  fixed
  w-full
  z-20
  top-0
  left-0
  border-b
  border-gray-200
  `}
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
text-blue-600
 mt-2`}
`;

const NavItems = styled.div`
  ${tw`
items-center
justify-between
w-full
hidden
md:flex
md:w-auto
md:order-1
`}
`;

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
bg-gray-50
md:flex-row
md:space-x-8
md:mt-0
md:border-0
md:bg-white
`}
`;

const NavItem = styled.a`
  ${tw`
block
py-2
pl-3
pr-4
text-gray-900
rounded
hover:bg-gray-100
md:hover:bg-transparent
md:hover:text-blue-700
md:p-0
`}
`;
