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
import com.a606.jansori.domain.todo.event.NagGenerateEvent;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagGenerateEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  private final String title = "육남매의 잔소리 폭격";
  private final String body = "회원님의 잔소리가 다른 사용자의 Todo에 작성되었습니다.";

  @EventListener(classes = {NagGenerateEvent.class})
  public void createNotificationByNagGenerate(final NagGenerateEvent nagGenerateEvent) {

    sb.setLength(0);
    Todo todo = nagGenerateEvent.getTodo();

    String content = sb.append(todo.getMember().getNickname()).append("님의 \"")
        .append(todo.getContent()).append("\"에 잔소리를 남겼습니다.").toString();
    Member receiver = todo.getNag().getMember();
    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.MYNAGONTODO);

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
