package com.a606.jansori.infra.message.controller;

import com.a606.jansori.infra.message.dto.NotificationMessage;
import com.a606.jansori.infra.message.service.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
public class FCMNotificationController {

  private final FirebaseMessagingService firebaseMessagingService;

//  @PostMapping
//  public String sendNotificationByToken(@RequestBody NotificationMessage notificationMessage){
//    return firebaseMessagingService.sendNotificationByToken(notificationMessage);
//  }


}
