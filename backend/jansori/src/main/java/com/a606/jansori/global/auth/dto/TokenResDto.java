package com.a606.jansori.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResDto {

  private String grantType;

  private String accessToken;

  private String refreshToken;

  private Long accessTokenExpiresIn;
  private Long memberId;

  public void update(Long memberId){
    this.memberId = memberId;
  }

}
