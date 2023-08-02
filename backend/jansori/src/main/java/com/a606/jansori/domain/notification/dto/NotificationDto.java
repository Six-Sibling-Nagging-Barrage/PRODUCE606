package com.a606.jansori.domain.notification.dto;

import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.domain.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDto {

  private Long notificationId;

  private NotificationType notificationType;

  private String content;

  private LocalDateTime createdAt;

  public static NotificationDto from(Notification notification){
    return NotificationDto.builder()
        .notificationId(notification.getId())
        .notificationType(notification.getNotificationType())
        .content(notification.getContent())
        .createdAt(notification.getCreatedAt())
        .build();
  }
}

/*
    return TodoDto.builder()
        .todoId(todo.getId())
        .display(todo.getDisplay())
        .finished(todo.getFinished())
        .content(todo.getContent())
        .todoAt(todo.getTodoAt())
        .tags(todo.getTodoTags().stream()
            .map(TagDto::from)
            .collect(Collectors.toList()))
        .feedTodoPersonaDtos(todo.getTodoPersonas().stream()
            .map(FeedTodoPersonaDto::from)
            .collect(Collectors.toList()))
        .build();
 */