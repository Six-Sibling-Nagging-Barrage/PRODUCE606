package com.a606.jansori.infra.message.controller;

import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.message.dto.PostMessageReqDto;
import com.a606.jansori.infra.message.service.FcmService;
import lombok.RequiredArgsConstructor;
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
  private EnvelopeResponse<Void> registerMemberMessagingToken(
      @RequestBody String fcmTokenFromClient) {

    fcmService.registerToken(fcmTokenFromClient);

    return EnvelopeResponse.<Void>builder().build();
  }

}
