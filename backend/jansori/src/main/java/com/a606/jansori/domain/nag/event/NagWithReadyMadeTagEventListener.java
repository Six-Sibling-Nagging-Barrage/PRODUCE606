package com.a606.jansori.domain.nag.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagWithReadyMadeTagEventListener {

  @EventListener(NagWithReadyMadeTagEvent.class)
  public void handle(NagWithReadyMadeTagEvent event) {

  }
}
