import { Routes, Route } from 'react-router-dom';
import './App.css';
import MainPage from './pages/MainPage';
import FeedPage from './pages/FeedPage';
import SignUpPage from './pages/SignUpPage';
import NagBoxPage from './pages/NagBoxPage';
import NagPage from './pages/NagBoxPage';
import ProfilePage from './pages/ProfilePage';
import ProfileEditPage from './pages/ProfileEditPage';
import CharacterInfoPage from './pages/CharacterInfoPage';
import NavBar from './components/NavBar/NavBar';
import { GoogleOAuthProvider } from '@react-oauth/google';

function App() {
  return (
    <div className='App'>
      <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_OAUTH_CLIENT_ID}>
        <NavBar />
      </GoogleOAuthProvider>
      <Routes>
        <Route path='/' element={<MainPage />} />
        <Route path='feed' element={<FeedPage />} />
        <Route path='signup' element={<SignUpPage />} />
        <Route path='nagbox' element={<NagBoxPage />} />
        <Route path='nag' elememnt={<NagPage />} />
        <Route path='profile' element={<ProfilePage />} />
        <Route path='profileedit' element={<ProfileEditPage />} />
        <Route path='characterinfo' element={<CharacterInfoPage />} />
      </Routes>
    </div>
  );
}

export default App;
