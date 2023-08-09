package com.a606.jansori.domain.notification.controller;

import com.a606.jansori.domain.notification.dto.PatchNotificationsReqDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsResDto;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @PatchMapping
  public EnvelopeResponse<PatchNotificationsResDto> patchNotifications(
      PatchNotificationsReqDto patchNotificationsReqDto){

    return EnvelopeResponse.<PatchNotificationsResDto>builder()
        .data(notificationService.patchNotifications(patchNotificationsReqDto))
        .build();
  }
}
