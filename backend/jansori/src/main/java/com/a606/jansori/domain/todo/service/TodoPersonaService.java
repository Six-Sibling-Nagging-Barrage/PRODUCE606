package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.service.NagRandomGenerator;
import com.a606.jansori.domain.persona.domain.PersonaReaction;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.exception.ReactionForbiddenException;
import com.a606.jansori.domain.persona.exception.TodoPersonaBusinessException;
import com.a606.jansori.domain.persona.exception.TodoPersonaNotFoundException;
import com.a606.jansori.domain.persona.repository.PersonaReactionRepository;
import com.a606.jansori.domain.persona.repository.TodoPersonaRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.GetTodoPersonaDetailsResDto;
import com.a606.jansori.domain.todo.dto.PostPersonaReactResDto;
import com.a606.jansori.domain.todo.event.PostPersonaReactionEvent;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoPersonaService {

  private final TodoRepository todoRepository;

  private final TodoPersonaRepository todoPersonaRepository;

  private final PersonaReactionRepository personaReactionRepository;

  private final NagRandomGenerator nagRandomGenerator;

  private final SecurityUtil securityUtil;

  private final ApplicationEventPublisher publisher;

  @Transactional(readOnly = true)
  public GetTodoPersonaDetailsResDto getTodoPersonas(Long todoId) {

    Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

    return GetTodoPersonaDetailsResDto.fromTodoPersonas(todoPersonaRepository.findAllByTodo(todo));
  }

  @Transactional
  public PostPersonaReactResDto postPersonaReaction(Long todoId, Long todoPersonaId) {

    Member member = securityUtil.getCurrentMemberByToken();

    Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

    TodoPersona todoPersona = todoPersonaRepository.findById(todoPersonaId)
        .orElseThrow(TodoPersonaNotFoundException::new);

    if (todoPersona.getTodo() != todo) {
      throw new TodoPersonaBusinessException();
    }

    personaReactionRepository
        .findByMemberAndTodoPersona(member, todoPersona)
        .ifPresent(personaReaction -> {
          throw new ReactionForbiddenException();
        });

    personaReactionRepository.save(PersonaReaction.builder()
        .todoPersona(todoPersona)
        .member(member)
        .todo(todo)
        .build());

    Integer likeCount = todoPersona.increaseLikeCount();

    if (likeCount == 1) {
      todoPersona.setLine(nagRandomGenerator.getRandomLineOfPersona(todoPersona.getPersona()));
      publisher.publishEvent(new PostPersonaReactionEvent(todoPersona));
    }

    return PostPersonaReactResDto.from(todoPersona);
  }
}
