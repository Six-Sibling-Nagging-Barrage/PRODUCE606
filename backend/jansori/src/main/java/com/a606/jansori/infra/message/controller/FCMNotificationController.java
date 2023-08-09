package com.a606.jansori.infra.message.controller;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.message.dto.PostFcmTokenReqDto;
import com.a606.jansori.infra.message.service.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class FCMNotificationController {

  private final FirebaseMessagingService firebaseMessagingService;

  @PostMapping("/token/register")
  private EnvelopeResponse<Void> registerToken(PostFcmTokenReqDto postFcmTokenReqDto) {
    return EnvelopeResponse.<Void>builder().build();
  }

//  @PostMapping
//  public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
//    return firebaseMessagingService.sendNotificationByToken(notificationMessage);
//  }


}
