import React from 'react';
import { styled } from 'twin.macro';

const HashTagItem = (props) => {
  const { hashTag, editable, setHashTagList } = props;
  const backgroundColors = [
    'rgba(173, 0, 255, 0.18)',
    'rgba(136, 118, 247, 0.3)',
    'rgba(0,148,255,0.18)',
  ];
  const fontColors = ['rgb(173, 0, 255)', 'rgb(136, 118, 247)', 'rgb(0,148,255)'];

  const handleRemoveHashTag = () => {
    setHashTagList((prev) => {
      return prev.filter((item) => item.tagName !== hashTag.tagName);
    });
  };

  const getRandomColor = () => {
    return backgroundColors[hashTag.tagId % 3];
  };

  const getRandomFontColor = () => {
    return fontColors[hashTag.tagId % 3];
  };

  return (
    <HashTag style={{ backgroundColor: getRandomColor(), color: getRandomFontColor() }}>
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
  padding: 5px 7px;
  border-radius: 5px;
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
