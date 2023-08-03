package com.a606.jansori.domain.notification.dto;

import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.domain.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class NotificationDto {

  private Long notificationId;

  private String content;

  private LocalDateTime createdAt;

  public static NotificationDto from(Notification notification){
    return NotificationDto.builder()
        .notificationId(notification.getId())
        .content(notification.getContent())
        .createdAt(notification.getCreatedAt())
        .build();
  }
}