package com.a606.jansori.domain.todo.event;

import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPersonaReactionEvent {

  private TodoPersona todoPersona;
  private NotificationType notificationType;
}
