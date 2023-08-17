import React from 'react';
import styled from 'styled-components';

const SimpleModal = (props) => {
  const { children, setIsModalOpen } = props;

  const handleClose = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <Background onClick={handleClose} />
      <ModalSection>{children}</ModalSection>
      <div>
        {/* 확인했을 경우 미션 실제 수행 */}
        <input type='button' value='확인' onClick={handleClose} />
        <input type='button' value='취소' onClick={handleClose} />
      </div>
    </>
  );
};

export default SimpleModal;

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
  transform: translate(-50%, -50%);
  z-index: 30;
`;
