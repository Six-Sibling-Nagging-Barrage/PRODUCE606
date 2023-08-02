package com.a606.jansori.domain.notification.controller;

import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

//  @PostMapping
//  public EnvelopeResponse<?> getNotifications(){
//
//    return EnvelopeResponse.<?>builder()
//        .data()
//  }
}
