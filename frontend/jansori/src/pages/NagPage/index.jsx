import React from 'react';
import Background from '../../components/UI/Background';
import NagForm from './components/NagForm';
import { addTokenToHeaders } from '../../apis/utils/authInstance';
import { useRecoilValue } from 'recoil';
import { memberTokenState } from '../../states/user';

const NagPage = () => {
  const jwtToken = useRecoilValue(memberTokenState);

  addTokenToHeaders(jwtToken);
  return (
    <Background>
      <NagForm />
    </Background>
  );
};

export default NagPage;
