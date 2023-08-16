package com.a606.jansori.domain.todo.event;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonaReactionEvent {

  private TodoPersona todoPersona;
}
