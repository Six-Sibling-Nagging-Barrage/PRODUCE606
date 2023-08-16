package com.a606.jansori.domain.nag.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.event.NagLikeEvent;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagLikeEventListener {

  private StringBuilder sb = new StringBuilder();

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  private final String title = "육남매의 잔소리 폭격";
  private final String body = "사람들이 회원님의 잔소리를 좋아합니다.";

  @EventListener(classes = {NagLikeEvent.class})
  public void createNotificationByNagLike(final NagLikeEvent nagLikeEvent) {

    sb.setLength(0);
    Member member = nagLikeEvent.getMember();
    Nag nag = nagLikeEvent.getNag();

    String content = sb.append(member.getNickname()).append("님이 ")
        .append(nag.getMember().getNickname()).append("님의 잔소리를 좋아합니다.").toString();
    Member receiver = nag.getMember();
    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.NAGREACTION);

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
