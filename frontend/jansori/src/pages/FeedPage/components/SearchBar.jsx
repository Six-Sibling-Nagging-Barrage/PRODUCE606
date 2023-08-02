import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';
import AutoComplete from '../../../components/AutoComplete/AutoComplete';

const SearchBar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchValue, setSearchValue] = useState('');

  const handleSearchInputChange = (event) => {
    setSearchValue(event.target.value);
    setIsOpen(true);
  };

  return (
    <SearchBarContainer>
      <SearchInput
        type="text"
        value={searchValue}
        placeholder="해시태그로 피드를 검색하세요."
        onChange={handleSearchInputChange}
        onFocus={() => setIsOpen(true)}
        onBlur={() => setIsOpen(false)}
      />
      {isOpen && (
        <AutoComplete
          searchValue={searchValue}
          setSearchValue={setSearchValue}
          setIsOpen={setIsOpen}
        />
      )}
    </SearchBarContainer>
  );
};

const SearchBarContainer = styled.div`
  ${tw`w-2/5`}
  padding: 10px 0;
  margin: 0 auto;
`;
const SearchInput = styled.input`
  ${tw`p-2 w-full border `}
  &:focus {
  }
`;

export default SearchBar;
