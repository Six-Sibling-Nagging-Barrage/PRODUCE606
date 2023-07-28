package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.service.NagRandomGenerator;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.repository.PersonaRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.GetTodoListResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.dto.TagDto;
import com.a606.jansori.domain.todo.dto.TodoDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final TodoRepository todoRepository;

  private final TagRepository tagRepository;

  private final MemberRepository memberRepository;

  private final PersonaRepository personaRepository;

  private final NagRandomGenerator nagRandomGenerator;

  private final Clock clock;

  @Transactional
  public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto, Long memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    Todo todo = postTodoReqDto.getTodoWith(member);

    postTodoReqDto.getTags().stream()
        .forEach(tagDto -> {
          TodoTag todoTag = new TodoTag(getTagIfExistElseSave(tagDto));

          todoTag.setTodo(todo);
        });

    todo.setNag(nagRandomGenerator.getRandomNagWithTags(member, todo.getTodoTags()));

    List<Persona> personas = personaRepository.findAll();

    personas.stream().forEach(persona -> {
      TodoPersona todoPersona = TodoPersona.builder()
          .persona(persona)
          .build();

      todoPersona.setTodo(todo);
    });

    return new PostTodoResDto(todoRepository.save(todo).getId());
  }

  private Tag getTagIfExistElseSave(TagDto tagDto) {

    if (tagDto.getTagId() == -1) {

      return tagRepository.save(Tag.from(tagDto));
    }

    return tagRepository.findById(tagDto.getTagId())
        .orElseThrow(TagNotFoundException::new);
  }


  @Transactional(readOnly = true)
  public GetTodoListResDto getMyTodayTodo(Long memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    LocalDate current = LocalDate.now(clock);
    LocalDateTime today = current.atStartOfDay();
    LocalDateTime tomorrow = current.plusDays(1).atStartOfDay();

    List<TodoDto> todos = todoRepository.findAllByMemberAndCreatedAtBetweenOrderByCreatedAtDesc(
            member, today, tomorrow).stream()
        .map(TodoDto::from)
        .collect(Collectors.toList());

    return GetTodoListResDto.builder()
        .todos(todos)
        .build();
  }

  @Transactional(readOnly = true)
  public GetTodoListResDto getMyAllTodo(Long memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    List<TodoDto> todos = todoRepository.findAllByMemberOrderByCreatedAtDesc(member).stream()
        .map(TodoDto::from)
        .collect(Collectors.toList());

    return GetTodoListResDto.builder()
        .todos(todos)
        .build();
  }
}
