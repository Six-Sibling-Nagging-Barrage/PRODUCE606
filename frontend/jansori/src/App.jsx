import { Routes, Route } from 'react-router-dom';
import './App.css';
import MainPage from './pages/MainPage';
import FeedPage from './pages/FeedPage';
import SignUpPage from './pages/SignUpPage';
import NagBoxPage from './pages/NagBoxPage';
import NagPage from './pages/NagPage';
import ProfilePage from './pages/ProfilePage';
import CharacterInfoPage from './pages/CharacterInfoPage';
import NavBar from './components/NavBar/NavBar';
import FloatingButton from './components/FloatingButton/FloatingButton';
import LoginPage from './pages/LoginPage';
import InitialProfilePage from './pages/InitialProfilePage';
import PrivateRoute from './pages/PrivateRoute';

function App() {
  const token = localStorage.getItem('member_user_token');

  return (
    <div className="App">
      <NavBar />
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route
          path="/feed"
          element={
            <PrivateRoute authenticated={token} component={<FeedPage />} />
          }
        />
        <Route path="/signup" element={<SignUpPage />} />
        <Route
          path="/initialprofile"
          element={
            <PrivateRoute
              authenticated={token}
              component={<InitialProfilePage />}
            />
          }
        />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/nagbox"
          element={
            <PrivateRoute authenticated={token} component={<NagBoxPage />} />
          }
        />
        <Route
          path="/nag"
          element={
            <PrivateRoute authenticated={token} component={<NagPage />} />
          }
        />
        <Route
          path="/profile"
          element={
            <PrivateRoute authenticated={token} component={<ProfilePage />} />
          }
        />
        <Route path="/characterinfo" element={<CharacterInfoPage />} />
      </Routes>
      <FloatingButton />
    </div>
  );
}

export default App;
