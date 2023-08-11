import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';

const SnackBar = ({ message, onClose }) => {
  const [show, setShow] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShow(false);
      onClose();
    }, 3000); // 3초 후에 자동으로 닫힘

    return () => clearTimeout(timer);
  }, [onClose]); // Include onClose in the dependency array

  return (
    <SnackBarContainer show={show}>
      <SnackBarContent>{message}</SnackBarContent>
    </SnackBarContainer>
  );
};

export default SnackBar;

const SnackBarContainer = styled.div`
  ${tw`
    fixed
    bottom-6
    left-6
    p-4
    bg-zinc-500			
    text-white	
    rounded-lg
    transition-opacity
    duration-300
  `}
  ${(props) =>
    props.show ? tw`opacity-100` : tw`opacity-0 pointer-events-none`}
`;

const SnackBarContent = styled.div`
  ${tw`
    text-center
  `}
`;
