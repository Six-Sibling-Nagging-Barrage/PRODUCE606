import React from 'react';
import { styled } from 'twin.macro';

const HashTagItem = (props) => {
  const { hashTag } = props;

  return (
    <HashTag>
      <Text>{hashTag}</Text>
      <button>X</button>
    </HashTag>
  );
};

const HashTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 2px;
  padding: 5px;
  background-color: #b197fc;
  border-radius: 5px;
  color: white;
  font-size: 13px;
`;

const Text = styled.span`
  margin-right: 5px;
`;

export default HashTagItem;
