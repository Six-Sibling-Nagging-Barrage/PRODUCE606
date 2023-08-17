package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.service.NagRandomGenerator;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.repository.PersonaRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.dto.TagDto;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.GetTodoByDateReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoByDateResDto;
import com.a606.jansori.domain.todo.dto.GetTodoMonthlyExistenceReqDto;
import com.a606.jansori.domain.todo.dto.GetTodoMonthlyExistenceResDto;
import com.a606.jansori.domain.todo.dto.PatchTodoResDto;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.dto.TodoCacheDto;
import com.a606.jansori.domain.todo.exception.TodoBusinessException;
import com.a606.jansori.domain.todo.exception.TodoNotFoundException;
import com.a606.jansori.domain.todo.exception.TodoUnauthorizedException;
import com.a606.jansori.domain.todo.repository.TodoAt;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.global.event.NagDeliveryEvent;
import com.a606.jansori.global.event.TodoAccomplishmentEvent;
import com.a606.jansori.global.event.TodoWaitingNagEvent;
import com.a606.jansori.infra.redis.util.NagBoxStatisticsUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final MemberRepository memberRepository;

  private final TodoRepository todoRepository;

  private final TagRepository tagRepository;

  private final PersonaRepository personaRepository;

  private final NagRandomGenerator nagRandomGenerator;

  private final SecurityUtil securityUtil;
  private final NotificationTypeRepository notificationTypeRepository;

  private final ApplicationEventPublisher publisher;

  private final NagBoxStatisticsUtil nagBoxStatisticsUtil;

  @Transactional
  public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto) {

    Member member = securityUtil.getCurrentMemberByToken();

    Todo todo = postTodoReqDto.getTodoWith(member);

    if (postTodoReqDto.getTags().isEmpty() || postTodoReqDto.getTags().size() > 3) {
      throw new TodoBusinessException();
    }

    postTodoReqDto.getTags()
        .forEach(tagDto -> {
          TodoTag todoTag = new TodoTag(getTagIfExistElseSave(tagDto));

          todoTag.setTodo(todo);
        });

    List<Persona> personas = personaRepository.findAll();

    personas.forEach(persona -> {
      TodoPersona todoPersona = TodoPersona.builder()
          .persona(persona)
          .build();

      todoPersona.setTodo(todo);
    });

    Boolean isAllNewTags = postTodoReqDto.isAllNewTags();

    Nag nag = !isAllNewTags ? nagRandomGenerator.getRandomNagWithTags(member, todo.getTodoTags())
        .orElse(null) : null;

    if (isAllNewTags || nag == null) {

      Todo savedTodo = todoRepository.save(todo);

      publisher.publishEvent(new TodoWaitingNagEvent(TodoCacheDto.from(savedTodo)));

      return PostTodoResDto.from(savedTodo);
    }

    todo.setNag(nag);

    Todo savedTodo = todoRepository.save(todo);

    publisher.publishEvent(new NagDeliveryEvent(savedTodo));

    return PostTodoResDto.from(savedTodo);
  }

  @Transactional(readOnly = true)
  public GetTodoByDateResDto getMyTodoByDate(GetTodoByDateReqDto getTodoByDateReqDto) {

    Member member = securityUtil.getCurrentMemberByToken();

    return getMyTodosByReqDtoAndMember(member, getTodoByDateReqDto);
  }

  @Transactional(readOnly = true)
  public GetTodoByDateResDto getMemberTodoByDate(Long memberId,
      GetTodoByDateReqDto getTodoByDateReqDto) {

    Member viewer = securityUtil.getNullableMemberByToken();

    Member memberWhoViewed = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    if (viewer != null & memberWhoViewed.equals(viewer)) {
      return getMyTodosByReqDtoAndMember(memberWhoViewed, getTodoByDateReqDto);
    }

    LocalDate date = getTodoByDateReqDto.getDate();

    return GetTodoByDateResDto.from(
        todoRepository.findAllByMemberAndTodoAtIsAndDisplayTrueOrderByCreatedAtDesc(
            memberWhoViewed, date));
  }

  private GetTodoByDateResDto getMyTodosByReqDtoAndMember(Member member,
      GetTodoByDateReqDto getTodoByDateReqDto) {

    LocalDate date = getTodoByDateReqDto.getDate();

    return GetTodoByDateResDto.from(
        todoRepository.findAllByMemberAndTodoAtIsOrderByCreatedAtDesc(member, date));
  }

  @Transactional
  public PatchTodoResDto patchTodoAccomplishment(Long todoId) {

    Member member = securityUtil.getCurrentMemberByToken();
    Todo todo = todoRepository.findById(todoId).orElseThrow(TodoNotFoundException::new);
    NotificationType notificationType = notificationTypeRepository
        .findByTypeName(NotificationTypeName.TODO_WITH_YOUR_NAG_ACCOMPLISHED);

    if (todo.getMember() != member) {
      throw new TodoUnauthorizedException();
    }

    boolean isNotFinish = !todo.getFinished();
    nagBoxStatisticsUtil.updateTotalDoneTodoCount(isNotFinish);

    if (isNotFinish) {
      publisher.publishEvent(new TodoAccomplishmentEvent(todo));
    }

    return PatchTodoResDto.from(todo.toggleFinished());
  }

  private Tag getTagIfExistElseSave(TagDto tagDto) {

    if (tagDto.getTagId() == -1) {
      return tagRepository.save(tagDto.toNewTag());
    }

    Tag tag = tagRepository.findById(tagDto.getTagId())
        .orElseThrow(TagNotFoundException::new);

    if (!tag.getName().equals(tagDto.getTagName())) {
      throw new TagNotFoundException();
    }

    return tag;
  }

  @Transactional(readOnly = true)
  public GetTodoMonthlyExistenceResDto getTodoMonthlyExistence(Long memberId,
      GetTodoMonthlyExistenceReqDto getTodoMonthlyExistenceReqDto) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(MemberNotFoundException::new);

    int year = getTodoMonthlyExistenceReqDto.getYearMonth().getYear();
    int month = getTodoMonthlyExistenceReqDto.getYearMonth().getMonthValue();

    return new GetTodoMonthlyExistenceResDto(
        todoRepository.findAllTodoAtByMemberAndMonthAndYear(member, year, month).stream()
            .map(TodoAt::getTodoAt)
            .collect(Collectors.toList())
    );

  }
}
