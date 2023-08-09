import React, { useEffect, useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';

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
    if (!event.target.files[0]) {
      // 업로드 취소하면 기본 이미지 세팅
      return setNewProfileImg(
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
      );
    }
    setNewProfileImg(URL.createObjectURL(event.target.files[0]));
    setProfileImg(event.target.files[0]);
  };

  return (
    <ProfileImgContainer size={size}>
      <img
        src={newProfileImg}
        alt="Rounded avatar"
        onClick={handleProfileImgClick}
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
