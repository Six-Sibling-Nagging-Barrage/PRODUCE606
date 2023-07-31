import React from "react";
import tw, { styled } from "twin.macro";

const TodoItem = (props) => {
  const { currentTodo } = props;

  return (
    <TodoContainer>
      <TodoDone>
        <div className="finished">{currentTodo.finished ? "❌" : "✅"}</div>
      </TodoDone>
      <TodoContent>
        <div className="todo">{currentTodo.content}</div>
        <div>해시태그 자리</div>
      </TodoContent>
    </TodoContainer>
  );
};

export default TodoItem;

const TodoContainer = styled.div`
  margin-top: 20px;
`;

const TodoDone = styled.div``;

const TodoContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
