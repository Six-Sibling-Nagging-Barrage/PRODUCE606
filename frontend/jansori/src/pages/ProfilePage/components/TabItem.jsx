import React from 'react';
import { styled } from 'twin.macro';

const TabItem = (props) => {
  const { id, title, currentTab, setCurrentTab } = props;

  const handleClickTab = () => {
    setCurrentTab(id);
  };

  return (
    <TabButton onClick={handleClickTab} selected={id === currentTab}>
      {title}
    </TabButton>
  );
};

const TabButton = styled.button`
  width: 300px;
  height: 40px;
  border-bottom: none;
  border-radius: 10px 10px 0 0;
  font-weight: bold;
  color: gray;
  transition: color 0.15s ease-in;
  ${({ selected }) =>
    selected &&
    `
    color: black;
  `};
`;

export default TabItem;
