package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.todo.domain.WaitingTodo;
import com.a606.jansori.domain.todo.dto.TodoCacheDto;
import com.a606.jansori.domain.todo.repository.WaitingTodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingTodoService {

  private final WaitingTodoRepository waitingTodoRepository;

  public void hibernateTodo(TodoCacheDto todoCacheDto) {

    todoCacheDto.getTagIds().forEach((tagId) -> waitingTodoRepository.save(WaitingTodo.builder()
        .tagId(String.valueOf(tagId))
        .todoId(String.valueOf(todoCacheDto.getTodoId()))
        .build())
    );
  }
}
