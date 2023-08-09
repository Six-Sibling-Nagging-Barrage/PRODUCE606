import { Routes, Route } from "react-router-dom";
import "./App.css";
import MainPage from "./pages/MainPage";
import FeedPage from "./pages/FeedPage";
import SignUpPage from "./pages/SignUpPage";
import NagBoxPage from "./pages/NagBoxPage";
import NagPage from "./pages/NagPage";
import ProfilePage from "./pages/ProfilePage";
import ProfileEditPage from "./pages/ProfileEditPage";
import CharacterInfoPage from "./pages/CharacterInfoPage";
import NavBar from "./components/NavBar/NavBar";
import FloatingButton from "./components/FloatingButton/FloatingButton";
import LoginPage from "./pages/LoginPage";
import InitialProfilePage from "./pages/InitialProfilePage";
import "./firebase";

function App() {
  return (
    <div className="App">
      <NavBar />
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/feed" element={<FeedPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/initialprofile" element={<InitialProfilePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/nagbox" element={<NagBoxPage />} />
        <Route path="/nag" element={<NagPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/profileedit" element={<ProfileEditPage />} />
        <Route path="/characterinfo" element={<CharacterInfoPage />} />
      </Routes>
      <FloatingButton />
    </div>
  );
}

export default App;
