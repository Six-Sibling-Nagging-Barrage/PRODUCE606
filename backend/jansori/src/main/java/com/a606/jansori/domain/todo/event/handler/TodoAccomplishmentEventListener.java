package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.event.TodoAccomplishmentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TodoAccomplishmentEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  private final String title = "육남매의 잔소리 폭격";
  private final String body = "회원님의 잔소리가 남겨진 사용자의 Todo가 완료되었습니다.";

  @EventListener(classes = {TodoAccomplishmentEvent.class})
  public void createNotificationByTodoComplete(
      final TodoAccomplishmentEvent todoAccomplishmentEvent) {

    Todo todo = todoAccomplishmentEvent.getTodo();

    if (todo.getNag() == null) {
      return;
    }

    Member receiver = todo.getNag().getMember();

    NotificationType notificationType =
        notificationTypeRepository.findByTypeName(
            NotificationTypeName.TODO_WITH_YOUR_NAG_ACCOMPLISHED);

    if (isNotificationSettingOn(notificationType, receiver)) {
      notificationService.saveNotification(notificationType, todo.getContent(), receiver);
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
