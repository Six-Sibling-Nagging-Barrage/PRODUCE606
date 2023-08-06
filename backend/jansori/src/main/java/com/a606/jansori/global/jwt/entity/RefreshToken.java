package com.a606.jansori.global.jwt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Builder
@Entity
public class RefreshToken {

  @Id
  @Column(name = "rt_key")
  private String key;

  @Column(name = "rt_value")
  private String value;


  public RefreshToken(String key, String value) {
    this.key = key;
    this.value = value;
  }

  public RefreshToken updateValue(String token) {
    this.value = token;
    return this;
  }
}