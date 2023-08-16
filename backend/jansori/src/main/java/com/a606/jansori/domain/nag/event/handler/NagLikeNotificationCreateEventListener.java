package com.a606.jansori.domain.nag.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.event.NagLikeNotificationCreateEvent;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NagLikeNotificationCreateEventListener {

    private final NotificationSettingRepository notificationSettingRepository;
    private final NotificationTypeRepository notificationTypeRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final FcmService fcmService;

    private final String title = "육남매의 잔소리 폭격";
    private final String body = "사람들이 회원님의 잔소리를 좋아합니다.";

    @TransactionalEventListener(classes = {NagLikeNotificationCreateEvent.class}, phase = TransactionPhase.AFTER_COMMIT)
    public void pushNotification(final NagLikeNotificationCreateEvent nagLikeNotificationCreateEvent) {

        Member receiver = nagLikeNotificationCreateEvent.getNotification().getReceiver();
        NotificationType notificationType = nagLikeNotificationCreateEvent.getNotification().getNotificationType();
        List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

        if(isNotificationSettingOn(notificationType, receiver) && fcmTokens != null){
            for (FcmToken fcmToken : fcmTokens) {
                String targetToken = fcmToken.getFcmToken();
                fcmService.sendWebPushMessage(targetToken, title, body);
            }
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
