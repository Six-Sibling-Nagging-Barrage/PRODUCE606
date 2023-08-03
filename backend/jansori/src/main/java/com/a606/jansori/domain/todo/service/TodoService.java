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
import com.a606.jansori.domain.todo.dto.GetTodoByDateReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoByDateResDto;
import com.a606.jansori.domain.todo.dto.PatchTodoResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.tag.dto.TagDto;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.exception.TodoUnauthorizedException;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.LocalDate;
import java.util.List;
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

  private final SecurityUtil securityUtil;

  @Transactional
  public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto) {

    Member member = getMemberFromSecurityUtil();

    Todo todo = postTodoReqDto.getTodoWith(member);

    postTodoReqDto.getTags()
        .forEach(tagDto -> {
          TodoTag todoTag = new TodoTag(getTagIfExistElseSave(tagDto));

          todoTag.setTodo(todo);
        });

    todo.setNag(nagRandomGenerator.getRandomNagWithTags(member, todo.getTodoTags()));

    List<Persona> personas = personaRepository.findAll();

    personas.forEach(persona -> {
      TodoPersona todoPersona = TodoPersona.builder()
          .persona(persona)
          .build();

      todoPersona.setTodo(todo);
    });

    return PostTodoResDto.from(todoRepository.save(todo));
  }

  @Transactional(readOnly = true)
  public GetTodoByDateResDto getMyTodoByDate(GetTodoByDateReqDto getTodoByDateReqDto) {

    Member member = getMemberFromSecurityUtil();

    LocalDate date = getTodoByDateReqDto.getDate();

    return GetTodoByDateResDto.from(
        todoRepository.findAllByMemberAndTodoAtIsOrderByCreatedAtDesc(member, date));
  }

  @Transactional
  public PatchTodoResDto patchTodoAccomplishment(Long todoId) {

    Member member = getMemberFromSecurityUtil();

    Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);

    if (todo.getMember() != member) {
      throw new TodoUnauthorizedException();
    }

    return PatchTodoResDto.from(todo.toggleFinished());
  }

  private Member getMemberFromSecurityUtil() {

    return memberRepository.findById(securityUtil.getSessionMemberId())
        .orElseThrow(MemberNotFoundException::new);
  }

  private Tag getTagIfExistElseSave(TagDto tagDto) {

    if (tagDto.getTagId() == -1) {

      return tagRepository.save(tagDto.toNewTag());
    }

    return tagRepository.findById(tagDto.getTagId())
        .orElseThrow(TagNotFoundException::new);
  }
}
