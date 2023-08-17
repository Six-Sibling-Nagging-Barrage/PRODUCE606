package com.a606.jansori.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationBoxCheckResDto {

  private boolean isUnreadNotificationLeft;

}
