import React, { useState } from 'react';
import tw from 'twin.macro';

const SearchBar = () => {
  const [searchValue, setSearchValue] = useState('');

  const handleSearchInputChange = (event) => {
    setSearchValue(event.target.value);
  };

  return (
    <StyledDiv>
      <input
        type="text"
        value={searchValue}
        placeholder="검색어를 입력하세요."
        onChange={handleSearchInputChange}
      />
    </StyledDiv>
  );
};

const StyledDiv = tw.div`
    p-6 w-2/5 m-auto
`;

export default SearchBar;
