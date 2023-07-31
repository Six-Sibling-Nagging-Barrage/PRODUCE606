import { useGoogleLogin } from '@react-oauth/google';
import googleIcon from '../../assets/google_icon.png';
import { styled } from 'twin.macro';

const GoogleLoginButton = () => {
  const googleSocialLogin = useGoogleLogin({
    onSuccess: (codeResponse) => console.log(codeResponse),
    flow: 'auth-code',
  });
  return (
    <>
      <LoginContainer onClick={() => googleSocialLogin()}>
        <img src={googleIcon} alt='google_login' />
        <div>구글로 시작하기</div>
      </LoginContainer>
    </>
  );
};

const LoginContainer = styled.div`
  background-color: white;
  width: 300px;
  padding: 0.8em 1em;
  border-radius: 0.2em;
  margin-top: 0.7em;
  display: flex;
  align-items: center;
  font-weight: bold;
  box-shadow: 0 0 5px rgb(207, 207, 207);
  & > img {
    height: 1.5rem;
    margin-right: 0.7em;
    margin-left: 0.2em;
  }
  & > div {
    width: 100%;
    text-align: center;
  }
  &:hover {
    cursor: pointer;
  }
`;

export default GoogleLoginButton;
