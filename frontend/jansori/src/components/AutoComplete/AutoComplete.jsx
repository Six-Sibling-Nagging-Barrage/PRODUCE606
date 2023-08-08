import React, { useState, useEffect } from 'react';
import { styled } from 'twin.macro';
import { getTagsAutoComplete } from '../../apis/api/tag';

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
    let timerId;

    if (searchValue === '') {
      setAutoCompleteList([]);
    } else {
      // 타이머를 활용하여 API 요청 지연
      timerId = setTimeout(async () => {
        // 태그 자동완성 검색 api 호출
        const data = await getTagsAutoComplete(searchValue);

        if (!data) return;
        if (typeof setExists === 'function') {
          const existingTag = data?.tags.find(
            (tag) => tag.tagName === searchValue
          );
          if (existingTag) {
            setCurrentHashTag(existingTag);
            setExists(true);
          }
        }
        setAutoCompleteList(data?.tags);
      }, 100); // 지연 시간 설정 (300ms)

      // Cleanup 함수에서 타이머 해제
      return () => {
        clearTimeout(timerId);
      };
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
