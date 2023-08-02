package com.a606.jansori.domain.notification.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetNotificationsResDto {

  List<NotificationDto> notifications = new ArrayList<>();

  public static GetNotificationsResDto()
}
