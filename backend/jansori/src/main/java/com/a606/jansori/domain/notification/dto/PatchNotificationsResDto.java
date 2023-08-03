package com.a606.jansori.domain.notification.dto;

import com.a606.jansori.domain.notification.domain.Notification;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchNotificationsResDto {

  List<NotificationDto> notifications;

  public static PatchNotificationsResDto from(List<Notification> notifications, LocalDateTime readAt){

    return PatchNotificationsResDto.builder()
        .notifications(notifications.stream()
            .map(n -> NotificationDto.of(n, readAt))
            .collect(Collectors.toList()))
        .build();
  }
}