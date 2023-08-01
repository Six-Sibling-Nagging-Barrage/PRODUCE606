import { Routes, Route } from 'react-router-dom';
import './App.css';
import MainPage from './pages/MainPage';
import SignUpPage from './pages/SignUpPage';
import FeedPage from './pages/FeedPage';
// import MyTodoPage from './pages/MyTodoPage';
import NagBoxPage from './pages/NagBoxPage';
import ProfilePage from './pages/ProfilePage';
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
        <Route path='signup' element={<SignUpPage />} />
        <Route path='feed' element={<FeedPage />} />
        {/* <Route path='mytodo' element={<MyTodoPage />} /> */}
        <Route path='nagbox' element={<NagBoxPage />} />
        <Route path='profile' element={<ProfilePage />} />
        <Route path='signup' element={<SignUpPage />} />
        <Route path='characterinfo' element={<CharacterInfoPage />} />
      </Routes>
    </div>
  );
}

export default App;
