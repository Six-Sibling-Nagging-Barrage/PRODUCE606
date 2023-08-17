import React from 'react';
import tw, { css, styled } from 'twin.macro';
import Button from './Button';
import { altImageUrl } from '../../constants/image';
import { useImageErrorHandler } from '../../hooks/useImageErrorHandler';

const AlertModal = (props) => {
  const { children, setIsModalOpen, handleAccept, handleCancel, nag } = props;

  const handleImgError = useImageErrorHandler();

  const handleClose = () => {
    setIsModalOpen(false);
    document.body.style.overflow = 'visible'; // 스크롤 활성화
  };

  return (
    <>
      <Background onClick={handleClose} />
      <ModalSection>
        <Content>
          <CommentContainer>
            {nag.nagMember && (
              <Profile>
                <ProfileImg
                  src={
                    nag.nagMember.imageUrl
                      ? nag.nagMember.imageUrl
                      : altImageUrl
                  }
                  onError={handleImgError}
                />
                <NickName>{nag.nagMember.nickname}</NickName>
              </Profile>
            )}
            <Bubble>
              <CommentContentWrapper>
                {nag.preview ? nag.preview : nag.content}
              </CommentContentWrapper>
            </Bubble>
          </CommentContainer>
          <div>티켓 1장을 사용해 잔소리를 열어볼까요?</div>
        </Content>
        <Footer>
          {/* 확인했을 경우 미션 실제 수행 */}
          <Button onClick={handleAccept} normal>
            확인
          </Button>
          <Button onClick={handleCancel} cancel>
            취소
          </Button>
        </Footer>
      </ModalSection>
    </>
  );
};

const Background = styled.div`
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  z-index: 30;
`;

const ModalSection = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  width: 35%;
  transform: translate(-50%, -50%);
  z-index: 30;
  background-color: white;
  border-radius: 20px;
  padding: 20px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
`;

const Footer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin: 10px auto;
  width: fit-content;
`;

const Content = styled.div`
  padding: 16px 0;
`;

const CommentContainer = styled.div`
  display: flex;
  position: relative;
  & .profile-img {
    width: 50px;
  }
  margin: 10px 0;
`;

const Profile = styled.div`
  height: fit-content;
  width: 15%;
`;

const NickName = styled.div`
  margin-top: 5px;
  font-size: 12px;
`;

const ProfileImg = styled.img`
  ${(props) =>
    props.isMemberNag
      ? css`
          ${tw`w-10 h-10 rounded-full`}
        `
      : css`
          ${tw`w-14 h-14 rounded-full`}
        `}
  margin: 0 auto;
  object-fit: cover;
`;

const CommentContentWrapper = styled.div`
  position: relative;
  line-height: 18px;
  text-align: left;
  top: 50%;
  padding-right: 5px;
  font-size: 15px;
`;

const UnlockImg = styled.img`
  filter: invert(61%) sepia(0%) saturate(0%) hue-rotate(163deg) brightness(91%)
    contrast(83%);
`;

const Bubble = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: relative;
  min-height: 60px;
  height: fit-content;
  margin-left: 10px;
  width: 100%;
  background-color: rgb(244, 244, 244);
  border-radius: 20px;
  padding: 10px 20px;
  &:after {
    content: '';
    position: absolute;
    left: 0;
    top: 50%;
    width: 0;
    height: 0;
    border: 8px solid transparent;
    border-right-color: rgb(244, 244, 244);
    border-left: 0;
    margin-top: -8px;
    margin-left: -8px;
  }
`;

export default AlertModal;
