package com.a606.jansori.global.event;

import com.a606.jansori.domain.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NagLikeNotificationCreateEvent {

    private Notification notification;
}
