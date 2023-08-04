import React, { useState, useEffect } from 'react';
import { styled } from 'twin.macro';

const dummyData = {
  tagCount: 5,
  tags: [
    {
      tagId: 23,
      tagName: '뉴진스',
    },
    {
      tagId: 24,
      tagName: '하니뉴진스',
    },
    {
      tagId: 22,
      tagName: '뉴진스하니',
    },
    {
      tagId: 20,
      tagName: '뉴진스민지',
    },
  ],
};

const AutoComplete = (props) => {
  const {
    searchValue,
    setSearchValue,
    setIsOpen,
    setSpecificTag,
    setExists,
    addHashTag,
    setCurrentHashTag,
  } = props;

  const [autoCompleteList, setAutoCompleteList] = useState([]);

  useEffect(() => {
    if (searchValue === '') {
      setAutoCompleteList([]);
    } else {
      // TODO: 태그 자동완성 검색 api 호출

      if (typeof setExists === 'function') {
        const existingTag = dummyData.tags.find((tag) => tag.tagName === searchValue);
        if (existingTag) {
          setCurrentHashTag(existingTag);
          setExists(true);
        }
      }
      setAutoCompleteList(dummyData.tags);
    }
  }, [searchValue]);

  const handleSelectAutoComplete = (item) => {
    setSearchValue('');
    if (typeof setSpecificTag === 'function') {
      setSpecificTag(item.tagId);
    }
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
            key={item.tagId}
            onMouseDown={() => {
              handleSelectAutoComplete(item);
            }}
          >
            {item.tagName}
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
