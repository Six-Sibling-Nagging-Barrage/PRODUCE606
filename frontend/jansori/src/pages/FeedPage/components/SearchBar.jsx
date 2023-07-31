import React, { useState, useEffect } from 'react';
import tw, { styled } from 'twin.macro';

const dummyData = [
  '운동',
  '오운완',
  '운동어쩌구',
  '공부',
  '공부하기싫다',
  '프로젝트',
  '프론트엔드',
  '백엔드',
];

const SearchBar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [searchValue, setSearchValue] = useState('');
  const [autoCompleteList, setAutoCompleteList] = useState([]);

  useEffect(() => {
    if (searchValue === '') {
      setIsOpen(false);
      setAutoCompleteList([]);
    } else {
      const filteredData = dummyData.filter((item) =>
        item.includes(searchValue)
      );
      setAutoCompleteList(filteredData);
    }
  }, [searchValue]);

  const handleSearchInputChange = (event) => {
    setSearchValue(event.target.value);
    setIsOpen(true);
  };

  const handleSelectAutoComplete = (value) => {
    setSearchValue(value);
    setIsOpen(false);
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
      )}
    </SearchBarContainer>
  );
};

const SearchBarContainer = styled.div`
  ${tw`w-2/5`}
  padding: 20px 0;
  margin: 0 auto;
`;
const SearchInput = styled.input`
  ${tw`p-2 w-full border `}
  &:focus {
  }
`;
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

export default SearchBar;
