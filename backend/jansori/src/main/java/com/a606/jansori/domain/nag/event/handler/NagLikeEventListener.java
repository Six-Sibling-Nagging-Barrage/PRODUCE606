package com.a606.jansori.domain.nag.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.event.NagLikeEvent;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagLikeEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final FcmTokenRepository fcmTokenRepository;
  private final FcmService fcmService;

  @EventListener(classes = {NagLikeEvent.class})
  public void createNotificationByNagLike(final NagLikeEvent nagLikeEvent) {

    sb.setLength(0);
    Member member = nagLikeEvent.getMember();
    Nag nag = nagLikeEvent.getNag();

    NotificationType notificationType = nagLikeEvent.getNotificationType();
    String content = sb.append(member.getNickname()).append("님이 ")
        .append(nag.getMember().getNickname()).append("님의 잔소리를 좋아합니다.").toString();
    Member receiver = nag.getMember();

    notificationService.createAndSaveNotification(notificationType, content, receiver);
  }

  @EventListener(classes = {NagLikeEvent.class})
  public void pushNotification(final NagLikeEvent nagLikeEvent) {

    Member receiver = nagLikeEvent.getNag().getMember();

    List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

    if (fcmTokens == null) {
      return;
    }

    for (FcmToken fcmToken : fcmTokens) {

      String targetToken = fcmToken.getFcmToken();
      String title = "육남매의 잔소리 폭격";
      String body = "사람들이 회원님의 잔소리를 좋아합니다.";

      fcmService.sendWebPushMessage(targetToken, title, body);
    }
  }

}
