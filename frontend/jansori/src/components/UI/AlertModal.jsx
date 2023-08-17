import React from 'react';
import styled from 'styled-components';
import Button from './Button';

const AlertModal = (props) => {
  const { children, setIsModalOpen, handleAccept, handleCancel } = props;

  const handleClose = () => {
    setIsModalOpen(false);
    document.body.style.overflow = 'visible'; // 스크롤 활성화
  };

  return (
    <>
      <Background onClick={handleClose} />
      <ModalSection>
        <Content>{children}</Content>
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

export default AlertModal;
