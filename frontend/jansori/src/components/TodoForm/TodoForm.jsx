import React, { useState } from "react";
import tw, { styled } from "twin.macro";
import moment from "moment";
import Mark from "../UI/Mark";
import Button from "../UI/Button";
import Toggle from "../UI/Toggle";

const TodoForm = () => {
  const [todo, setTodo] = useState();
  // const [hashtag, setHashtag] = useState();
  const [isPublic, setIsPublic] = useState(true);

  const toggleHanlder = () => {
    setIsPublic(!isPublic);
  };

  return (
    <TodoFormContainer>
      <Mark label={"todo"} />
      <TodoFormBox>
        <label className="col-span-1">TO-DO</label>
        <input className="col-span-2" type="text" id={todo}></input>
        <label className="col-span-1">해시태그</label>
        {/* 해시태그 검색 들어갈 자리 */}
        <input className="col-span-2" type="text" id={todo}></input>
        <label className="col-span-1">공개여부</label>
        {/* 토글 들어갈 자리 */}
        <div className="col-span-2">
          <Toggle isPublic={isPublic} onToggle={toggleHanlder} todoInput />
        </div>
        <label className="col-span-1">날짜</label>
        <div className="col-span-2">{moment().format("YYYY-MM-DD")}</div>
        <ButtonLocation>
          <Button type="submit" label={"Add"} normal />
        </ButtonLocation>
      </TodoFormBox>
    </TodoFormContainer>
  );
};

export default TodoForm;

const TodoFormContainer = styled.div`
  ${tw`
  relative
  rounded
  w-96
  border-2
  pb-16`}
`;

const TodoFormBox = styled.form`
  ${tw`
grid
grid-cols-3 gap-4
`}
`;

const ButtonLocation = styled.div`
  ${tw`absolute
  bottom-2
  right-2`}
`;
