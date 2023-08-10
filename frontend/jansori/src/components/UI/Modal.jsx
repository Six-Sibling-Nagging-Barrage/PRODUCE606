import React, { useState } from 'react';
import styled, { css } from 'styled-components';

const Modal = (props) => {
  const { children, setIsModalOpen } = props;

  const handleClose = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Background onClick={handleClose} />
      <ModalSection>
        <Content>{children}</Content>
      </ModalSection>
    </>
  );
};

// const modalSettings = () => css`
//   visibility: ${visible ? 'visible' : 'hidden'};
//   z-index: 99;
//   transition: visibility 0.15s ease-out;
// `;

const Background = styled.div`
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 99;
`;

// ${(props) => modalSettings(props.visible)}

const ModalSection = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.8);
  padding: 15px 30px;
  z-index: 99;
  backdrop-filter: blur(3px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
  overflow: auto;
  max-height: 85%;
  /* ( 크롬, 사파리, 오페라, 엣지 ) 동작 */
  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* 파이어폭스 */
`;

const Title = styled.div`
  display: flex;
  justify-content: flex-end;
  padding: 0 16px;
`;

const Content = styled.div`
  padding: 16px 0;
`;

const CloseButton = styled.button`
  border: none;
  background: none;
  cursor: pointer;
`;

export default Modal;
