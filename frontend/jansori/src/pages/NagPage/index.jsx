import React from 'react';
import tw, { styled } from 'twin.macro';
import Background from '../../components/UI/Background';
import NagForm from './components/NagForm';

const NagPage = () => {
  return (
    <Background>
      <NagFormWrap>
        <NagForm />
      </NagFormWrap>
    </Background>
  );
};

export default NagPage;

// const NagFormWrap = styled.div`
//   ${tw`bg-white w-1/2 mt-10`}
// `;
const NagFormWrap = styled.div`
  position: absolute;
  top: 55%;
  left: 50%;
  width: 40%;
  @media screen and (max-width: 768px) {
    width: 70%;
  }
  @media screen and (max-width: 1024px) {
    width: 50%;
  }
  height: 40vh;
  transform: translate(-50%, -50%);
  background-color: rgba(255, 255, 255, 0.5);
  padding: 16px;
  z-index: 99;
  backdrop-filter: blur(10px);
  border-radius: 5px;
  box-shadow: 0 0 100px rgba(0, 0, 0, 0.3);
`;
