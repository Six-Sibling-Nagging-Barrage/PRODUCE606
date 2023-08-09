package com.a606.jansori.infra.message.controller;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.message.dto.PostFcmTokenReqDto;
import com.a606.jansori.infra.message.dto.PostMessageReqDto;
import com.a606.jansori.infra.message.service.FcmService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

  private final FcmService fcmService;

  @PostMapping("/token/register")
  private EnvelopeResponse<Void> registerMemberToken(PostFcmTokenReqDto postFcmTokenReqDto) {

    fcmService.registerToken(postFcmTokenReqDto);

    return EnvelopeResponse.<Void>builder().build();
  }

  @PostMapping("/send")
  public ResponseEntity pushMessage(@RequestBody PostMessageReqDto postMessageReqDto)
    throws IOException {
    fcmService.sendMessageTo(postMessageReqDto.getFcmToken(), postMessageReqDto.getTitle(),
        postMessageReqDto.getBody());
    return ResponseEntity.ok().build();
  }

//  @PostMapping
//  public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
//    return firebaseMessagingService.sendNotificationByToken(notificationMessage);
//  }


}
