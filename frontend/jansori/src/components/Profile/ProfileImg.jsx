import React, { useEffect, useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';
import { altImageUrl } from '../../constants/image';

const ProfileImg = (props) => {
  const { editable, profileImg, setProfileImg, size } = props;

  const [newProfileImg, setNewProfileImg] = useState();
  const profileImgInput = useRef(null);

  useEffect(() => {
    if (profileImg) {
      setNewProfileImg(profileImg);
    }
  }, []);

  const handleProfileImgClick = () => {
    if (!editable) return;
    profileImgInput.current.click();
  };

  const handleProfileImgUpload = (event) => {
    // console.log(URL.createObjectURL(event.target.files[0]));
    if (!event.target.files[0]) return;
    setNewProfileImg(URL.createObjectURL(event.target.files[0]));
    setProfileImg(event.target.files[0]);
  };

  const handleImgError = (e) => {
    e.target.src = altImageUrl;
  };

  return (
    <ProfileImgContainer size={size}>
      <img
        src={newProfileImg}
        onClick={handleProfileImgClick}
        onError={handleImgError}
      />
      <input
        type="file"
        accept="image/jpg,impge/png,image/jpeg"
        name="profile-img"
        onChange={handleProfileImgUpload}
        ref={profileImgInput}
      />
    </ProfileImgContainer>
  );
};

const ProfileImgContainer = styled.div`
  width: fit-content;
  margin: 0 auto;
  margin-bottom: 15px;
  & > img {
    ${tw`rounded-full`}
    object-fit: cover;
    width: ${({ size }) => size};
    height: ${({ size }) => size};
  }
  & > input {
    display: none;
  }
`;

export default ProfileImg;
