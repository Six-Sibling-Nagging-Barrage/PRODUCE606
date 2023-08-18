package com.a606.jansori.global.event;

import com.a606.jansori.domain.todo.dto.TodoCacheDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoWaitingNagEvent {

  private TodoCacheDto todoCacheDto;
}
