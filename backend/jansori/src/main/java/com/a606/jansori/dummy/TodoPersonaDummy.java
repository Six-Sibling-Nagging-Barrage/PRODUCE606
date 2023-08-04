package com.a606.jansori.dummy;

import com.a606.jansori.domain.persona.domain.Line;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.repository.LineRepository;
import com.a606.jansori.domain.persona.repository.PersonaRepository;
import com.a606.jansori.domain.persona.repository.TodoPersonaRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TodoPersonaDummy {

  private final TodoPersonaRepository todoPersonaRepository;

  private final PersonaRepository personaRepository;

  private final LineRepository lineRepository;

  public List<TodoPersona> createTodoPersonas(List<Todo> todos) {

    List<TodoPersona> todoPersonas = new ArrayList<>();

    List<Persona> personas = personaRepository.findAll();

    for (Persona persona : personas) {

      List<Line> lines = lineRepository.findAllByPersona(persona);

      for (Todo todo : todos) {
        int randomLineIndex = (int) (Math.random() * lines.size());

        todoPersonas.add(TodoPersona.builder()
            .persona(persona)
            .todo(todo)
            .line(lines.get(randomLineIndex))
            .build());
      }
    }

    return todoPersonaRepository.saveAll(todoPersonas);
  }
}
