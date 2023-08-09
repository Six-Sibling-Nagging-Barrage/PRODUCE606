import React, { useEffect, useState } from 'react';
import { styled } from 'twin.macro';
import AutoComplete from '../AutoComplete/AutoComplete';
import HashTagItem from './HashTagItem';

const HashTag = (props) => {
  const {
    editable,
    hashTagLimit,
    setSpecificTag,
    hashTagList,
    setHashTagList,
  } = props;

  const [hashTagInput, setHashTagInput] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [hashTagExists, setHashTagExists] = useState(false);
  const [hashTagCount, setHashTagCount] = useState(0);
  const [currentHashTag, setCurrentHashTag] = useState(null);

  useEffect(() => {
    if (!hashTagList) return;
    setHashTagCount(hashTagList.length);
  }, [hashTagList]);

  const handleHashTagInputChange = (event) => {
    setHashTagInput(event.target.value);
    setIsOpen(true);
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault();

      if (hashTagExists) {
        addHashTag(currentHashTag);
        setIsOpen(false);
      }
    }
  };

  const addHashTag = (item) => {
    for (const tag of hashTagList) {
      if (tag.tagId === item.tagId) return setHashTagInput('');
    }
    setHashTagList((prev) => {
      return [...prev, item];
    });
    setHashTagCount((prev) => prev + 1);
    setHashTagInput('');
  };

  return (
    <>
      <HashTagContainer className="hashtag-container">
        {hashTagList?.map((hashTag, index) => {
          return (
            <HashTagItem
              key={hashTag.tagId}
              hashTag={hashTag}
              editable={editable}
              setHashTagList={setHashTagList}
            />
          );
        })}
        {hashTagCount < hashTagLimit && (
          <HashTagInput
            type="text"
            placeholder="해시태그를 검색하세요."
            onChange={handleHashTagInputChange}
            value={hashTagInput}
            onKeyPress={handleKeyPress}
            onBlur={() => setIsOpen(false)}
          />
        )}
      </HashTagContainer>
      {isOpen && (
        <AutoComplete
          searchValue={hashTagInput}
          setSearchValue={setHashTagInput}
          setIsOpen={setIsOpen}
          setSpecificTag={setSpecificTag}
          setExists={setHashTagExists}
          addHashTag={addHashTag}
          setCurrentHashTag={setCurrentHashTag}
        />
      )}
    </>
  );
};

const HashTagContainer = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  min-height: 50px;
  padding: 10px;
  margin: 0 auto;
  width: 100%;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 5px;
`;

const HashTagInput = styled.input`
  display: inline-flex;
  min-width: 100px;
  background: transparent;
  border: none;
  outline: none;
  cursor: text;
  margin-left: 4px;
  &:hover {
    .hashtag-container:has(&) {
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
    }
  }
`;

export default HashTag;
