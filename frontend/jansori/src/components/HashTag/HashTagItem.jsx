import React from 'react';
import { styled } from 'twin.macro';
import { hashTagColor } from '../../constants/hashtag';

const HashTagItem = (props) => {
  const { hashTag, editable, setHashTagList } = props;

  const handleRemoveHashTag = () => {
    setHashTagList((prev) => {
      return prev.filter((item) => item.tagName !== hashTag.tagName);
    });
  };

  const getRandomColor = () => {
    return hashTagColor[Math.abs(hashTag?.tagId) % 3].background;
  };

  const getRandomFontColor = () => {
    return hashTagColor[Math.abs(hashTag?.tagId) % 3].font;
  };

  return (
    <HashTag
      style={{ backgroundColor: getRandomColor(), color: getRandomFontColor() }}
    >
      <Text>{hashTag.tagName}</Text>
      {editable && <RemoveButton onClick={handleRemoveHashTag}>X</RemoveButton>}
    </HashTag>
  );
};

const HashTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 2px;
  padding: 6px 8px;
  border-radius: 10px;
  color: white;
  font-size: 13px;
  width: fit-content;
  height: fit-content;
`;

const Text = styled.span`
  font-weight: bold;
`;

const RemoveButton = styled.div`
  cursor: pointer;
  margin-left: 5px;
`;

export default HashTagItem;
