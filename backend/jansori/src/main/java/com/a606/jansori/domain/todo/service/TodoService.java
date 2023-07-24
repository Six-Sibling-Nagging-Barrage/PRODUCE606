package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    @Transactional
    public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto) {

        Todo todo = Todo.builder().build();

        return new PostTodoResDto(todoRepository.save(todo).getId());
    }

}
