package com.a606.jansori.domain.todo.event;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoCompleteEvent {

  private Todo todo;
}
