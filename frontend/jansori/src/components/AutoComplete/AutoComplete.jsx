import React, { useState, useEffect } from 'react';
import { styled } from 'twin.macro';
import { getTagsAutoComplete } from '../../apis/api/tag';

const AutoComplete = (props) => {
  const {
    searchValue,
    setSearchValue,
    setIsOpen,
    setSpecificTag,
    addHashTag,
    autoCompleteList,
    setAutoCompleteList,
  } = props;

  const [isRecommended, setIsRecommended] = useState(false);

  useEffect(() => {
    let timerId;
    // 검색어 입력 안 하면 추천검색어
    if (searchValue === '') {
      setIsRecommended(true);
    } else {
      setIsRecommended(false);
    }
    // 타이머를 활용하여 API 요청 지연
    timerId = setTimeout(async () => {
      // 태그 자동완성 검색 api 호출
      const data = await getTagsAutoComplete(searchValue);
      if (!data) return;
      setAutoCompleteList(data?.tags);
    }, 100);

    // Cleanup 함수에서 타이머 해제
    return () => {
      clearTimeout(timerId);
    };
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
      {searchValue && autoCompleteList.length === 0 ? (
        <>
          <NoResult>일치하는 해시태그가 없어요</NoResult>
          <NoResult>
            엔터를 눌러 해시태그를
            <br />
            생성해보세요 🤓
          </NoResult>
        </>
      ) : (
        <>
          {isRecommended && <li>💡 추천 해시태그 💡</li>}
          {autoCompleteList.map((item, index) => (
            <DropDownItem
              key={item.tagId}
              onMouseDown={() => {
                handleSelectAutoComplete(item);
              }}
            >
              {item.tagName}
            </DropDownItem>
          ))}
        </>
      )}
    </DropDownList>
  );
};

const DropDownList = styled.ul`
  position: absolute;
  margin: 0 auto;
  padding: 8px;
  background-color: white;
  border-top: none;
  border-radius: 0 0 5px 5px;
  list-style-type: none;
  z-index: 1000;
  min-width: 200px;
  font-size: 15px;
  max-height: 30vh;
  overflow: auto;
`;

const DropDownItem = styled.li`
  padding: 5px 10px;
  &:hover {
    cursor: pointer;
    background-color: #f0f0f0;
  }
`;

const NoResult = styled.li`
  padding: 5px 10px;
`;

export default AutoComplete;
