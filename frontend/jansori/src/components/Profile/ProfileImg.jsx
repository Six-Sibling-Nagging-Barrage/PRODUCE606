import React, { useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';

const ProfileImg = (props) => {
  const { editable } = props;

  const [profileImg, setProfileImg] = useState(
    'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
  );
  const profileImgInput = useRef(null);

  const handleProfileImgClick = () => {
    if (!editable) return;
    profileImgInput.current.click();
  };

  const handleProfileImgUpload = (event) => {
    // console.log(URL.createObjectURL(event.target.files[0]));
    if (!event.target.files[0]) {
      // 업로드 취소하면 기본 이미지 세팅
      return setProfileImg(
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'
      );
    }
    setProfileImg(URL.createObjectURL(event.target.files[0]));
  };

  return (
    <ProfileImgContainer>
      <img
        src={profileImg}
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
  margin-bottom: 20px;
  & > img {
    ${tw`w-24 h-24 rounded-full`}
    object-fit: cover;
  }
  & > input {
    display: none;
  }
`;

export default ProfileImg;
