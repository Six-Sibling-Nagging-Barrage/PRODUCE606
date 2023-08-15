import React from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import tw, { css, styled } from 'twin.macro';
import { RxHamburgerMenu } from 'react-icons/rx';
import { TbTicket } from 'react-icons/tb';
import { Link } from 'react-router-dom';
import {
  memberIdState,
  ticketState,
  navBarState,
  isLoginState,
  profileImgState,
} from '../../states/user';
import logoImg from '../../assets/jansori-logo-eating-removebg-preview.png';
import notificationIcon from '../../assets/notification_icon.webp';
import { menus, beforeLoginMenus } from '../../constants/nav';
import { useImageErrorHandler } from '../../hooks/useImageErrorHandler';
import {
  isHamburgerOpenState,
  isProfileModalOpenState,
  isNotificationModalOpenState,
} from '../../states/navBar';
import DropdownProfileMenu from './DropdownProfileMenu';
import { altImageUrl } from '../../constants/image';
import NotificationList from './NotificationList';

const NavBar = () => {
  const [istoggleopen, setIsToggleOpen] = useRecoilState(isHamburgerOpenState);
  const [isProfileModalOpen, setIsProfileModalOpen] = useRecoilState(isProfileModalOpenState);
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useRecoilState(
    isNotificationModalOpenState
  );
  const [currentMenu, setCurrentMenu] = useRecoilState(navBarState);

  const isLogin = useRecoilValue(isLoginState);
  const profileImg = useRecoilValue(profileImgState);
  const memberId = useRecoilValue(memberIdState);
  const ticket = useRecoilValue(ticketState);

  const handleImgError = useImageErrorHandler();

  const handleHamburgerMenuClick = () => {
    setIsToggleOpen(!istoggleopen);
  };

  const handleProfileClick = () => {
    setIsProfileModalOpen(!isProfileModalOpen);
    setIsNotificationModalOpen(false);
  };

  const handleNotificationClick = () => {
    setIsNotificationModalOpen(!isNotificationModalOpen);
    setIsProfileModalOpen(false);
  };

  const handleMenuClick = (index) => {
    setIsToggleOpen(false);
    setIsProfileModalOpen(false);
    setIsNotificationModalOpen(false);
    setCurrentMenu(index);
  };

  return (
    <>
      <Nav>
        {/* 로고 들어가는 부분 시작 */}
        <NavWrap>
          <Logo href='/' onClick={() => handleMenuClick(-1)}>
            <img src={logoImg} />
          </Logo>
          {/* 네비게이션 리스트 부분 시작 */}
          <NavItems id='navbar-sticky' istoggleopen={istoggleopen ? 'true' : undefined}>
            <NavItemsUl>
              {menus.map((menu, index) => {
                const url =
                  menu.to === '/profile'
                    ? `${menu.to}?id=${encodeURIComponent(memberId)}`
                    : menu.to;
                return (
                  <li key={index}>
                    <NavItem
                      to={url}
                      onClick={() => handleMenuClick(index)}
                      active={currentMenu === index ? 'active' : null}
                    >
                      {menu.name}
                    </NavItem>
                  </li>
                );
              })}
            </NavItemsUl>
          </NavItems>
          {/* 네비게이션 리스트 부분 끝 */}
          {/* 오른쪽 로그인 버튼 부분 시작*/}
          <RightButtons>
            {isLogin ? (
              <AfterLoginWrap>
                <TicketWrap>
                  <TicketItemLogo>
                    <TbTicket />
                  </TicketItemLogo>
                  <TicketItem>{ticket}</TicketItem>
                </TicketWrap>
                <ul>
                  <li>
                    <Avatar onClick={handleProfileClick}>
                      <img src={profileImg ? profileImg : altImageUrl} onError={handleImgError} />
                    </Avatar>
                  </li>
                  {isProfileModalOpen && (
                    <li>
                      <DropdownProfileMenu />
                    </li>
                  )}
                </ul>
                <ul>
                  <li>
                    <BellWrap onClick={handleNotificationClick}>
                      <img src={notificationIcon} width='25px' />
                    </BellWrap>
                  </li>
                  {isNotificationModalOpen && (
                    <li>
                      <NotificationList />
                    </li>
                  )}
                </ul>
              </AfterLoginWrap>
            ) : (
              <>
                <NavItemsUl>
                  {beforeLoginMenus.map((menu, index) => {
                    return (
                      <li>
                        <NavItem
                          to={menu.to}
                          onClick={() => handleMenuClick(menu.index)}
                          active={currentMenu === menu.index ? 'active' : null}
                        >
                          {menu.name}
                        </NavItem>
                      </li>
                    );
                  })}
                </NavItemsUl>
              </>
            )}
            {/* 화면 작아졌을 때 햄버거 icon 시작 */}
            <HamburgerButton
              type='button'
              aria-controls='navbar-sticky'
              aria-expanded={istoggleopen}
              onClick={handleHamburgerMenuClick}
            >
              <span className='sr-only'>Open</span>
              <RxHamburgerMenu className='w-5 h-5' aria-hidden='true' />
            </HamburgerButton>
            {/* 화면 작아졌을 때 햄버거 icon 끝 */}
          </RightButtons>
          {/* 오른쪽 로그인 버튼 부분 끝*/}
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
`;

const NavWrap = styled.div`
  ${tw`
    max-w-screen-2xl
    flex
    flex-wrap
    mx-auto
    p-4
  `}
  width: 100%;
  position: relative;
`;

const Logo = styled.a`
  ${tw`
  flex
  items-center
  `}
  position: absolute;
  left: 20px;
  & > img {
    width: 75px;
  }
`;

const RightButtons = styled.div`
  ${tw`
    flex
    md:order-2
  `}
  position: absolute;
  right: 20px;
  height: 40px;
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

const NavItems = styled.div(({ istoggleopen }) => [
  tw`
    items-center
    justify-between
    w-full
    hidden
    md:flex
    md:w-auto
    md:order-1
  `,
  css`
    margin: 0 auto;
  `,
  istoggleopen && tw`flex`,
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
    text-gray-900
    rounded
    py-2
    lg:px-3
  `}
  border-radius: 30px;
  &:hover {
    cursor: pointer;
    background-color: rgba(125, 125, 125, 0.3);
  }
  ${({ active }) =>
    active === 'active' &&
    `
  background-color: rgba(125, 125, 125, 0.3);
  `}
`;

const AfterLoginWrap = styled.div`
  ${tw`flex`}
`;

const Avatar = styled.button`
  line-height: 50px;
  margin: 0 10px;
  & > img {
    ${tw`w-10 h-10 rounded-full `}
  }
`;

const TicketWrap = styled.div`
  ${tw`flex items-center`}
  border-radius: 20px;
  padding: 0 10px;
  height: 40px;
  font-size: 15px;
  font-weight: 500;
  color: rgb(91, 43, 134);
  border: 1.5px solid rgb(91, 43, 134);
  & > img {
    width: 20px;
  }
`;

const TicketItem = styled.div`
  ${tw`flex items-center`}
`;

const TicketItemLogo = styled.div`
  transform: rotate(-30deg);
  margin-right: 10px;
`;

const BellWrap = styled.button`
  margin-top: 7px;
`;
