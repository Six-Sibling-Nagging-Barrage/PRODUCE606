import React, { useRef, useState } from 'react';
import tw, { styled } from 'twin.macro';
import { altImageUrl } from '../../constants/image';
import { useImageErrorHandler } from '../../hooks/useImageErrorHandler';

const ProfileImg = (props) => {
  const { editable, newProfileImg, setNewProfileImg, size } = props;

  const profileImgInput = useRef(null);

  const [currentProfileImg, setCurrentProfileImg] = useState(newProfileImg);

  const handleImgError = useImageErrorHandler();

  const handleProfileImgClick = () => {
    if (!editable) return;
    profileImgInput.current.click();
  };

  const handleProfileImgUpload = (event) => {
    if (!event.target.files[0]) return setNewProfileImg(altImageUrl);
    setNewProfileImg(event.target.files[0]);
    setCurrentProfileImg(URL.createObjectURL(event.target.files[0]));
  };

  const handleRemoveImg = () => {
    setNewProfileImg(altImageUrl);
    setCurrentProfileImg(altImageUrl);
    profileImgInput.current.value = '';
  };

  return (
    <ProfileImgContainer size={size}>
      <img
        src={currentProfileImg ? currentProfileImg : altImageUrl}
        onClick={handleProfileImgClick}
        onError={handleImgError}
      />
      <input
        type="file"
        accept="image/jpg,image/png,image/jpeg"
        name="profile-img"
        onChange={handleProfileImgUpload}
        ref={profileImgInput}
      />
      {editable && newProfileImg && newProfileImg !== altImageUrl && (
        <RemoveImg onClick={handleRemoveImg}>이미지 삭제</RemoveImg>
      )}
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

const RemoveImg = styled.div`
  cursor: pointer;
  margin: 10px 0;
  color: gray;
  font-size: 14px;
`;

export default ProfileImg;
