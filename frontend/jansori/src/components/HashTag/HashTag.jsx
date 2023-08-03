import React, { useState } from 'react';
import { styled } from 'twin.macro';
import AutoComplete from '../AutoComplete/AutoComplete';
import HashTagItem from './HashTagItem';

const HashTag = () => {
  const [hashTagInput, setHashTagInput] = useState('');
  const [isOpen, setIsOpen] = useState(false);
  const [hashTagExists, setHashTagExists] = useState(false);
  const [hashTagList, setHashTagList] = useState([]);

  const handleHashTagInputChange = (event) => {
    setHashTagInput(event.target.value);
    setIsOpen(true);
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter' && hashTagExists) {
      addHashTag(hashTagInput);
    }
  };

  const addHashTag = (item) => {
    setHashTagList((prev) => {
      console.log(prev);
      return [...prev, item];
    });
    setHashTagInput('');
  };

  return (
    <>
      <HashTagContainer>
        {hashTagList.map((hashTag, index) => {
          return (
            <HashTagItem
              key={index}
              hashTag={hashTag}
              editable={true}
              setHashTagList={setHashTagList}
            />
          );
        })}
        <HashTagInput
          type="text"
          placeholder="해시태그를 추가하세요."
          onChange={handleHashTagInputChange}
          value={hashTagInput}
          onKeyPress={handleKeyPress}
        />
      </HashTagContainer>
      {isOpen && (
        <AutoComplete
          searchValue={hashTagInput}
          setSearchValue={setHashTagInput}
          setIsOpen={setIsOpen}
          setExists={setHashTagExists}
          addHashTag={addHashTag}
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
  margin: 10px auto;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 5px;
  margin-bottom: 30px;
`;

const HashTagInput = styled.input`
  display: inline-flex;
  min-width: 100px;
  background: transparent;
  border: none;
  outline: none;
  cursor: text;
  margin-left: 4px;
`;

export default HashTag;
