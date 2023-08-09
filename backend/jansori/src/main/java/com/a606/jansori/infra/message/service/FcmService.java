package com.a606.jansori.infra.message.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.dto.FcmMessage;
import com.a606.jansori.infra.message.dto.PostFcmTokenReqDto;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FcmService {

  private final SecurityUtil securityUtil;
  private final FcmTokenRepository fcmTokenRepository;
  private final FirebaseMessaging firebaseMessaging;
  private final String API_URL
      = "https://fcm.googleapis.com/v1/projects/jansori-393804/messages:send";
  private final ObjectMapper objectMapper;


  public void sendMessageTo(String targetToken, String title, String body) throws IOException {
    String message = makeMessage(targetToken, title, body);

    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = RequestBody
        .create(MediaType.parse("application/json; charset=utf-8"), message);
    Request request = new Request.Builder()
        .url(API_URL)
        .post(requestBody)
        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
        .build();

    Response response = client.newCall(request).execute();

    System.out.println(response.body().string());
  }

  private String makeMessage(String targetToken, String title, String body)
      throws JsonParseException, JsonProcessingException {
    FcmMessage fcmMessage = FcmMessage.builder()
        .message(FcmMessage.Message.builder()
            .token(targetToken)
            .notification(FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build()
            ).build()).validateOnly(false).build();

    return objectMapper.writeValueAsString(fcmMessage);
  }

  private String getAccessToken() throws IOException {
    String firebaseConfigPath = "firebase/firebase_service_key.json";

    GoogleCredentials googleCredentials = GoogleCredentials
        .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

    googleCredentials.refreshIfExpired();
    return googleCredentials.getAccessToken().getTokenValue();
  }

  @Transactional
  public void registerToken(PostFcmTokenReqDto postFcmTokenReqDto){

    Member member = securityUtil.getCurrentMemberByToken();

    List<FcmToken> tokens = fcmTokenRepository.findAllByMember(member);

    boolean tokenExists = tokens
        .stream().anyMatch(fcmToken ->
            fcmToken.getFcmToken()
                .equals(postFcmTokenReqDto.getFcmToken()));

    if(!tokenExists){
      fcmTokenRepository.save(FcmToken.builder()
          .fcmToken(postFcmTokenReqDto.getFcmToken())
          .member(member)
          .build());
    }

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
