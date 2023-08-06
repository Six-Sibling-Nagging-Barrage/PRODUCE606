import googleIcon from '../../assets/google_icon.png';
import { styled } from 'twin.macro';
import { createMember } from '../../apis/api/member';
import { useSetRecoilState } from 'recoil';
import { isLogin } from '../../states/user';

const GoogleLoginButton = () => {
  const setIsLoginState = useSetRecoilState(isLogin);

  const clickLogin = () => {
    setIsLoginState(true);
    createMember();
  };

  return (
    <>
      <LoginContainer onClick={clickLogin}>
        <div>
          <img src={googleIcon} />
          구글로 시작하기
        </div>
      </LoginContainer>
    </>
  );
};

const LoginContainer = styled.div`
  background-color: white;
  width: 20vw;
  @media (max-width: 1000px) {
    width: 30vw;
  }
  @media (max-width: 700px) {
    width: 40vw;
  }
  padding: 0.8em 1em;
  border-radius: 0.2em;
  margin-top: 0.7em;
  display: flex;
  align-items: center;
  font-weight: bold;
  box-shadow: 0 0 5px rgb(207, 207, 207);
  & img {
    display: inline-block;
    height: 1.5rem;
    margin-right: 0.7em;
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
