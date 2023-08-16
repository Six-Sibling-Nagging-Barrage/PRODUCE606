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
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import java.time.Clock;
import java.util.List;
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

    sb.setLength(0);
    Todo todo = todoAccomplishmentEvent.getTodo();

    if (todo.getNag() == null) {
      return;
    }

    String content = sb.append(todo.getNag().getMember().getNickname()).append("님의 잔소리가 남겨진 ")
            .append(todo.getMember().getNickname()).append("님의 Todo \"")
            .append(todo.getContent()).append("\"가 완료되었습니다.").toString();
    Member receiver = todo.getNag().getMember();
    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.TODOACCOMPLISHMENT);

    if(isNotificationSettingOn(notificationType, receiver)){
      notificationService.createAndSaveNotification(notificationType, content, receiver, title, body);
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
