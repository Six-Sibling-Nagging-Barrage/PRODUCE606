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
  private final NotificationService notificationService;
  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  @EventListener(classes = {NagLikeEvent.class})
  public void createNotificationByNagLike(final NagLikeEvent nagLikeEvent) {

    Nag nag = nagLikeEvent.getNag();

    Member receiver = nag.getMember();
    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.NAG_LIKED);

    if(isNotificationSettingOn(notificationType, receiver)){
      notificationService.saveNotification(notificationType, nag.getContent(), receiver);
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
