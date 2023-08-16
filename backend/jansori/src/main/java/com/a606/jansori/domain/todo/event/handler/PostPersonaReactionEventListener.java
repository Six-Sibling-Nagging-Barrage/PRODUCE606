package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.todo.event.PostPersonaReactionEvent;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostPersonaReactionEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  private final String title = "육남매의 잔소리 폭격";
  private final String body = "회원님의 Todo에 캐릭터 잔소리가 달렸습니다.";

  @EventListener(classes = {PostPersonaReactionEvent.class})
  public void createNotificationByWritePersonaNagOnTodo(
      final PostPersonaReactionEvent postPersonaReactionEvent) {

    sb.setLength(0);
    TodoPersona todoPersona = postPersonaReactionEvent.getTodoPersona();

    String content = sb.append(todoPersona.getPersona().getName()).append("가 \"")
        .append(todoPersona.getTodo().getContent()).append("\"에 잔소리를 남겼습니다.").toString();
    Long talkerId = todoPersona.getPersona().getId();
    Member receiver = todoPersona.getTodo().getMember();
    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.NAGONMYTODO);

    if(isNotificationSettingOn(notificationType, receiver)) {
      notificationService.createAndSaveNotification(notificationType, content,
              talkerId, TalkerType.PERSONA, receiver, title, body);
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
