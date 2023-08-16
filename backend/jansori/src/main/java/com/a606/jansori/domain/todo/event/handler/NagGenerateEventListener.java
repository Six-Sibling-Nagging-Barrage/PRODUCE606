package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationType;
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

  private final FcmTokenRepository fcmTokenRepository;
  private final FcmService fcmService;

  @EventListener(classes = {NagGenerateEvent.class})
  public void createNotificationByNagGenerate(final NagGenerateEvent nagGenerateEvent) {

    sb.setLength(0);
    Todo todo = nagGenerateEvent.getTodo();

    NotificationType notificationType = nagGenerateEvent.getNotificationType();
    String content = sb.append(todo.getMember().getNickname()).append("님의 \"")
        .append(todo.getContent()).append("\"에 잔소리를 남겼습니다.").toString();
    Member receiver = todo.getNag().getMember();

    notificationService.createAndSaveNotification(notificationType, content, receiver);

  }

  @EventListener(classes = {NagGenerateEvent.class})
  public void pushNotification(final NagGenerateEvent nagGenerateEvent) {

    Member receiver = nagGenerateEvent.getTodo().getNag().getMember();

    List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

    if (fcmTokens == null) {
      return;
    }

    for (FcmToken fcmToken : fcmTokens) {

      String targetToken = fcmToken.getFcmToken();
      String title = "육남매의 잔소리 폭격";
      String body = "회원님의 잔소리가 다른 사용자의 Todo에 작성되었습니다.";

      fcmService.sendWebPushMessage(targetToken, title, body);
    }
  }
}
