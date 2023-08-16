package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.event.PersonaReactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonaReactionEventListener {

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  @EventListener(classes = {PersonaReactionEvent.class})
  public void createNotificationByWritePersonaNagOnTodo(
      final PersonaReactionEvent personaReactionEvent) {

    TodoPersona todoPersona = personaReactionEvent.getTodoPersona();

    Todo todo = todoPersona.getTodo();

    Member receiver = todoPersona.getTodo().getMember();
    NotificationType notificationType =
        notificationTypeRepository.findByTypeName(NotificationTypeName.MY_TODO_GOT_NEW_NAG);

    if (isNotificationSettingOn(notificationType, receiver)) {
      notificationService.saveNotification(notificationType, todo, todoPersona.getPersona());
    }
  }

  private Boolean isNotificationSettingOn(NotificationType notificationType, Member member) {

    NotificationSetting notificationSetting =
        notificationSettingRepository.findByNotificationTypeAndMember(notificationType, member);

    if (notificationSetting == null) {
      throw new NotificationSettingNotFoundException();
    }

    return notificationSetting.getActivated();
  }

}
