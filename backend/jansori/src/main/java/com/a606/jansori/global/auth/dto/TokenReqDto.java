package com.a606.jansori.global.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenReqDto {

  private String accessToken;

  private String refreshToken;

}
