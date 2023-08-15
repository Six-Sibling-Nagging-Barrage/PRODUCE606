package com.a606.jansori.domain.todo.event;

import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.TodoCacheDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoWaitingNagEvent {

  private TodoCacheDto todoCacheDto;
}
