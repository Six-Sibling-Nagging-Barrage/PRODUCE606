package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.repository.TodoPersonaRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.GetLineDetailsResDto;
import com.a606.jansori.domain.todo.dto.PostPersonaReactReqDto;
import com.a606.jansori.domain.todo.dto.PostPersonaReactResDto;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoPersonaService {

  private final MemberRepository memberRepository;

  private final TodoRepository todoRepository;

  private final TodoPersonaRepository todoPersonaRepository;

  @Transactional(readOnly = true)
  public GetLineDetailsResDto getTodoPersonas(Long todoId) {

    Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

    return GetLineDetailsResDto.fromTodoPersonas(todoPersonaRepository.findAllByTodo(todo));
  }

  @Transactional
  public PostPersonaReactResDto postPersonaReaction(Long memberId, Long todoId,
      PostPersonaReactReqDto postPersonaReactReqDto) {

    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    TodoPersona todoPersona = todoPersonaRepository.findByTodo_IdAndPersona_Id(todoId,
        postPersonaReactReqDto.getPersonaId())
        .ifPresentOrElse(
            ()->{},
            ()->{}
        );

    return null;
  }
}
