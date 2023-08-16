package com.a606.jansori.domain.notification.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.event.NotificationCreateEvent;
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
public class NotificationCreateEventListener {

    private final FcmTokenRepository fcmTokenRepository;
    private final FcmService fcmService;

    @TransactionalEventListener(classes = {NotificationCreateEvent.class}, phase = TransactionPhase.AFTER_COMMIT)
    public void pushNotification(final NotificationCreateEvent notificationCreateEvent) {

        Member receiver = notificationCreateEvent.getNotification().getReceiver();
        List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

        for (FcmToken fcmToken : fcmTokens) {
            String targetToken = fcmToken.getFcmToken();
            fcmService.sendWebPushMessage(
                    targetToken, notificationCreateEvent.getTitle(), notificationCreateEvent.getBody());
        }
    }
}
