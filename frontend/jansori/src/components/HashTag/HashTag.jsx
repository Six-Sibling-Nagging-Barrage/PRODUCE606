import React, { useEffect, useState } from 'react';
import { styled } from 'twin.macro';
import AutoComplete from '../AutoComplete/AutoComplete';
import HashTagItem from './HashTagItem';

const HashTag = (props) => {
  const {
    editable,
    creatable,
    hashTagLimit,
    setSpecificTag,
    hashTagList,
    setHashTagList,
    absolute,
  } = props;

  const [hashTagInput, setHashTagInput] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [hashTagCount, setHashTagCount] = useState(0);
  const [autoCompleteList, setAutoCompleteList] = useState([]);
  const [lengthError, setLengthError] = useState(false);

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
      const tagInput = hashTagInput.trim();

      if (!tagInput) return;
      if (tagInput.length < 2 || tagInput.length > 8) {
        setIsOpen(false);
        return setLengthError(true);
      }

      setLengthError(false);
      const findTag = autoCompleteList.find((tag) => tag.tagName === tagInput);
      if (findTag) {
        addHashTag(findTag);
        setIsOpen(false);
      } else {
        if (!creatable) return;
        addHashTag({
          tagId: -1,
          tagName: tagInput,
        });
        setIsOpen(false);
      }
    }
  };

  const addHashTag = (item) => {
    setLengthError(false);
    for (const tag of hashTagList) {
      if (tag.tagName === item.tagName) return setHashTagInput('');
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
              key={index}
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
            onFocus={() => setIsOpen(true)}
          />
        )}
      </HashTagContainer>
      {lengthError && (
        <ErrorMessage absolute={absolute}>
          태그는 2-8자로 입력해주세요.
        </ErrorMessage>
      )}
      {isOpen && (
        <AutoComplete
          searchValue={hashTagInput}
          setSearchValue={setHashTagInput}
          setIsOpen={setIsOpen}
          setSpecificTag={setSpecificTag}
          addHashTag={addHashTag}
          autoCompleteList={autoCompleteList}
          setAutoCompleteList={setAutoCompleteList}
          creatable={creatable}
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
  max-height: 190px;
  padding: 10px;
  margin: 0 auto;
  width: 100%;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 5px;
  overflow: auto;
`;

const HashTagInput = styled.input`
  display: inline-flex;
  width: 176px;
  background: transparent;
  border: none;
  outline: none;
  cursor: text;
  margin-left: 4px;
`;

const ErrorMessage = styled.div`
  ${({ absolute }) => absolute && 'position: absolute'};
  font-size: 14px;
  color: #ff6c6c;
  padding: 0 15px;
  width: fit-content;
`;

export default HashTag;
