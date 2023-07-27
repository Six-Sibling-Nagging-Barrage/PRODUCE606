import React from 'react';
import styled from 'styled-components';
import tw from 'twin.macro';
import { BiSearch } from 'react-icons/bi';
import Button from '../UI/Button';

const NavBar = () => {
  return (
    <Nav>
      {/* 로고 들어가는 부분 시작 */}
      <LogoWrap>
        <Logo href='/'>
          <img
            className='h-4'
            src='https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSyy01YZvIVHu61Ocu6oepgHZwHOzzoYHRn8g&usqp=CAU'
            alt='logo'
          />
        </Logo>
        {/* 오른쪽 로그인 버튼 부분 시작*/}
        <RightButtons>
          <BiSearch />
          <Button label={'로그인'} normal />
        </RightButtons>
        {/* 오른쪽 로그인 버튼 부분 끝*/}
      </LogoWrap>
      {/* 로고 들어가는 부분 끝 */}
      <NavItems>
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
    </Nav>
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

const LogoWrap = styled.div`
  ${tw`
max-w-screen-xl
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

const RightButtons = styled.div`
  ${`flex
md:order-2`}
`;

const NavItems = styled.div`
  ${tw`
items-center
justify-between
hidden
w-full
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
