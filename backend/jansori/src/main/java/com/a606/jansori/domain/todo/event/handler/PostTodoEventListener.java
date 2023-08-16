package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.event.PostTodoEvent;
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
public class PostTodoEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final FcmTokenRepository fcmTokenRepository;
  private final FcmService fcmService;

  // 이벤트 발생시 알림을 생성하고 알림함의 최근 알림 도착시간 업데이트
  @EventListener(classes = {PostTodoEvent.class})
  public void createNotificationByWriteMemberNagOnTodo(final PostTodoEvent postTodoEvent) {

    sb.setLength(0);
    Todo todo = postTodoEvent.getTodo();
    Nag nag = postTodoEvent.getTodo().getNag();

    NotificationType notificationType = postTodoEvent.getNotificationType();
    String content = sb.append(nag.getMember().getNickname()).append("님이 \"")
        .append(todo.getContent()).append("\"에 잔소리를 남겼습니다.").toString();
    Long talkerId = nag.getMember().getId();
    Member receiver = todo.getMember();

    notificationService.createAndSaveNotification(notificationType, content,
        talkerId, TalkerType.MEMBER, receiver);

  }

  @EventListener(classes = {PostTodoEvent.class})
  public void pushNotification(final PostTodoEvent postTodoEvent) {

    Member receiver = postTodoEvent.getTodo().getMember();

    List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

    if (fcmTokens == null) {
      return;
    }

    for (FcmToken fcmToken : fcmTokens) {

      String targetToken = fcmToken.getFcmToken();
      String title = "육남매의 잔소리 폭격";
      String body = "회원님의 Todo에 다른 사용자의 잔소리가 달렸습니다.";

      fcmService.sendWebPushMessage(targetToken, title, body);
    }
  }
}
