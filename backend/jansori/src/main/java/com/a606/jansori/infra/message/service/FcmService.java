package com.a606.jansori.infra.message.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.auth.exception.InvalidTokenException;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.infra.message.domain.FcmMessage;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class FcmService {

  private final SecurityUtil securityUtil;
  private final FcmTokenRepository fcmTokenRepository;
  private final ObjectMapper objectMapper;
  private final String API_URL;
  private final String API_SCOPE;
  private final String FIREBASE_CONFIG_PATH;

  public FcmService(@Value("${fcm.api.url}") String API_URL,
      @Value("${fcm.config.path}") String FIREBASE_CONFIG_PATH,
      @Value("${fcm.key.scope}") String API_SCOPE,
      SecurityUtil securityUtil,
      FcmTokenRepository fcmTokenRepository,
      ObjectMapper objectMapper) {

    this.API_SCOPE = API_SCOPE;
    this.API_URL = API_URL;
    this.FIREBASE_CONFIG_PATH = FIREBASE_CONFIG_PATH;
    this.securityUtil = securityUtil;
    this.fcmTokenRepository = fcmTokenRepository;
    this.objectMapper = objectMapper;

  }

  public void sendWebPushMessage(String targetToken, String title, String body) {

    String message = makeFcmMessage(targetToken, title, body);

    try {

      makeFcmWebPushRequest(message);

    } catch (IOException e) {

      throw new InvalidTokenException();

    }

  }

  private String makeFcmMessage(String targetToken, String title, String body) {

    FcmMessage fcmMessage = FcmMessage.builder()
        .message(FcmMessage.Message.builder()
            .token(targetToken)
            .notification(FcmMessage.Notification.builder()
                .title(title)
                .body(body)
                .image(null)
                .build()
            ).build()).validateOnly(false).build();

    try {

      return objectMapper.writeValueAsString(fcmMessage);

    } catch (JsonProcessingException e) {

      throw new JsonParseException();

    }
  }

  private void makeFcmWebPushRequest(String message) throws IOException {
    OkHttpClient client = new OkHttpClient();
    Request request;

    RequestBody requestBody = RequestBody
        .create(MediaType.parse("application/json; charset=utf-8"), message);

    request = new Request.Builder()
        .url(API_URL)
        .post(requestBody)
        .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
        .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
        .build();

    Response response = client.newCall(request).execute();
  }

  private String getAccessToken() throws IOException {

    GoogleCredentials googleCredentials = GoogleCredentials
        .fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())
        .createScoped(List.of(API_SCOPE));

    googleCredentials.refreshIfExpired();

    return googleCredentials.getAccessToken().getTokenValue();

  }

  @Transactional
  public void registerToken(String fcmTokenFromClient) {

    Member member = securityUtil.getCurrentMemberByToken();

    List<FcmToken> tokens = fcmTokenRepository.findAllByMember(member);

    boolean tokenExists = tokens
        .stream().anyMatch(fcmToken ->
            fcmToken.getFcmToken()
                .equals(fcmTokenFromClient));

    if (!tokenExists) {
      fcmTokenRepository.save(FcmToken.builder()
          .fcmToken(fcmTokenFromClient)
          .member(member)
          .build());
    }

  }

}
