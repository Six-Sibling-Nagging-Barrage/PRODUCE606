package com.a606.jansori.global.auth.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800)
public class RefreshToken {

  @Id
  private String email;

  private String refreshToken;

  public RefreshToken( final String email, final String refreshToken) {
    this.email = email;
    this.refreshToken = refreshToken;
  }
}