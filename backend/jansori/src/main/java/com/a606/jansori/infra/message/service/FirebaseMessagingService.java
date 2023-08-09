package com.a606.jansori.infra.message.service;

import com.a606.jansori.infra.message.dto.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseMessagingService {

  private final Map<Long, String> tokenMap = new HashMap<>();

  public void register(final Long userId, final String token) {
    tokenMap.put(userId, token);
  }
//  private final FirebaseMessaging firebaseMessaging;

//  public String sendNotificationByToken(NotificationMessage notificationMessage) {
//
//    Notification notification = Notification.builder()
//        .setTitle(notificationMessage.getTitle())
//        .setBody(notificationMessage.getBody())
//        .setImage(notificationMessage.getImage())
//        .build();
//
//    Message message = Message.builder()
//        .setToken(notificationMessage.getRecipientToken())
//        .setNotification(notification)
//        .putAllData(notificationMessage.getData())
//        .build();
//
//    try {
//      firebaseMessaging.send(message);
//      return "Success Sending Notification";
//    } catch (FirebaseMessagingException e) {
//      e.printStackTrace();
//      return "Error Sending Notification";
//    }
//
//
//  }


}
