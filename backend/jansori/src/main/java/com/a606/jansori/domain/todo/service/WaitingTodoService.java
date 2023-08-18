package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.todo.dto.TodoCacheDto;
import com.a606.jansori.infra.redis.util.WaitingTodoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingTodoService {

  private final WaitingTodoUtil waitingTodoUtil;

  public void hibernateTodo(TodoCacheDto todoCacheDto) {

    todoCacheDto.getTagIds().forEach((tagId) -> waitingTodoUtil.pushRear(tagId,
        todoCacheDto.getTodoId())
    );

  }
}
