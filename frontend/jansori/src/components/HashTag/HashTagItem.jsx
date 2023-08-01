import React from 'react';
import { styled } from 'twin.macro';

const HashTagItem = (props) => {
  const { hashTag, editable, setHashTagList } = props;

  const handleRemoveHashTag = () => {
    setHashTagList((prev) => {
      return prev.filter((item) => item !== hashTag);
    });
  };

  return (
    <HashTag>
      <Text>{hashTag}</Text>
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
  background-color: #b197fc;
  border-radius: 5px;
  color: white;
  font-size: 13px;
`;

const Text = styled.span``;

const RemoveButton = styled.button`
  margin-left: 5px;
`;

export default HashTagItem;