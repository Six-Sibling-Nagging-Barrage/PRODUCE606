package com.a606.jansori.domain.nag.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagPublishedTodoEventListener {

  @EventListener(NagPublishedTodoEvent.class)
  public void handle(NagPublishedTodoEvent event) {

  }
}
