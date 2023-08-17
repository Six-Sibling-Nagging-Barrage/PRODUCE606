package com.a606.jansori.global.event;

import com.a606.jansori.domain.todo.service.WaitingTodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoWaitingNagEventListener {

  private final WaitingTodoService waitingTodoService;

  @EventListener(TodoWaitingNagEvent.class)
  public void handleTodoWaitingNagEvent(TodoWaitingNagEvent event) {

    waitingTodoService.hibernateTodo(event.getTodoCacheDto());

  }
}
