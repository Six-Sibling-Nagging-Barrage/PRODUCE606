package com.a606.jansori.domain.notification.event;

import com.a606.jansori.domain.notification.domain.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationCreateEvent {

    private Notification notification;
    private String title;
    private String body;
}
