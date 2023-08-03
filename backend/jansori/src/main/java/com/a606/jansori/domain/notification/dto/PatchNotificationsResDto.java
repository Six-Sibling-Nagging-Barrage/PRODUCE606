package com.a606.jansori.domain.notification.dto;

import com.a606.jansori.domain.notification.domain.Notification;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class PatchNotificationsResDto {

  private List<NotificationDto> notifications;
  private LocalDateTime lastReadAt;

  public static PatchNotificationsResDto from(List<Notification> notifications, LocalDateTime lastReadAt){

    return PatchNotificationsResDto.builder()
        .lastReadAt(lastReadAt)
        .notifications(notifications.stream()
            .map(NotificationDto::from)
            .collect(Collectors.toList()))
        .build();
  }
}