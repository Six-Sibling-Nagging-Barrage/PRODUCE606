package com.a606.jansori.infra.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostMessageReqDto {

  private String fcmToken;
  private String title;
  private String body;

}
