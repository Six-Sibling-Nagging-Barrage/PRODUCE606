import React, { useState, useEffect } from "react";
import tw, { styled } from "twin.macro";
import TodoItem from "./TodoItem";

const TodoList = () => {
  const [todoList, setTodoList] = useState([]);

  useEffect(() => {
    setTodoList((prev) => [
      ...prev,
      ...Array.from({ length: 10 }, () => ({
        id: getId(),
        finished: false,
        date: "2023.07.31",
        content: "살려주세요!",
      })),
    ]);
  }, []);

  return (
    <TodoContainer>
      <ul>
        {todoList &&
          todoList.map((post) => {
            return <TodoItem currentTodo={post} key={post.id} />;
          })}
      </ul>
    </TodoContainer>
  );
};

export default TodoList;

const TodoContainer = styled.div``;

let id = 0;
function getId() {
  return id++;
}
