package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.service.NagRandomGenerator;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
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

  private final NotificationTypeRepository notificationTypeRepository;

  private final NotificationSettingRepository notificationSettingRepository;

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

    NotificationType notificationType = notificationTypeRepository
        .findByType(NotificationTypeName.NAGONMYTODO);

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

      // 캐릭터 잔소리가 달리는 상황에서 todo 주인의 알림설정이 수신 상태일 때 알림 이벤트 발생
      if(notificationSettingRepository.
          findByNotificationTypeAndMember(notificationType, todo.getMember()).getActivated()){
        publisher.publishEvent(new PostPersonaReactionEvent(todoPersona, notificationType));
      }
    }

    return PostPersonaReactResDto.from(todoPersona);
  }
}
