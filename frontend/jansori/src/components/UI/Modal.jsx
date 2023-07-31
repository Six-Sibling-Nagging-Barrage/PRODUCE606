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
        <Title>
          {/* <CloseButton type='button' onClick={handleClose}>
            X
          </CloseButton> */}
        </Title>
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
  background-color: rgba(0, 0, 0, 0.6);
  z-index: 99;
`;

// ${(props) => modalSettings(props.visible)}

const ModalSection = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 1);
  padding: 16px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.5);
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
