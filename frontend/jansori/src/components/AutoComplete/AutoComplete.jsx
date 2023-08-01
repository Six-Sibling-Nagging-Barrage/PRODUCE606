import React, { useState, useEffect } from 'react';
import { styled } from 'twin.macro';

const dummyData = [
  '뉴진스',
  '하니',
  '뉴진스하니',
  '하니귀여워',
  '민지귀여워',
  '뉴진스민지',
  '귀여워뉴진스',
  '어쩌구하니저쩌구',
];

const AutoComplete = (props) => {
  const { searchValue, setSearchValue, setIsOpen, setExists, addHashTag } =
    props;

  const [autoCompleteList, setAutoCompleteList] = useState([]);

  useEffect(() => {
    if (searchValue === '') {
      setAutoCompleteList([]);
    } else {
      if (typeof setExists === 'function') {
        setExists(dummyData.includes(searchValue));
      }
      const filteredData = dummyData.filter((item) =>
        item.includes(searchValue)
      );
      setAutoCompleteList(filteredData);
    }
  }, [searchValue]);

  const handleSelectAutoComplete = (item) => {
    setSearchValue(item);
    if (typeof addHashTag === 'function') {
      addHashTag(item);
    }
    setIsOpen(false);
  };

  return (
    <DropDownList>
      {autoCompleteList.length === 0 ? (
        <li>일치하는 해시태그가 없어요 ㅠㅠ</li>
      ) : (
        autoCompleteList.map((item, index) => (
          <DropDownItem
            key={index}
            onMouseDown={() => {
              handleSelectAutoComplete(item);
            }}
          >
            {item}
          </DropDownItem>
        ))
      )}
    </DropDownList>
  );
};

const DropDownList = styled.ul`
  position: absolute;
  display: block;
  margin: 0 auto;
  padding: 8px;
  background-color: white;
  border-top: none;
  border-radius: 0 0 5px 5px;
  box-shadow: 0 2px 10px rgb(0, 0, 0, 0.3);
  list-style-type: none;
  z-index: 3;
`;

const DropDownItem = styled.li`
  padding: 5px 10px;

  &:hover {
    cursor: pointer;
    background-color: #dfdfdf;
  }
`;

export default AutoComplete;
