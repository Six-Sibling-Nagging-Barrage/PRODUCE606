package com.a606.jansori.domain.todo.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoWaitingNagEventListener {

  @EventListener(TodoWaitingNagEvent.class)
  public void handle(TodoWaitingNagEvent event) {

  }
}
