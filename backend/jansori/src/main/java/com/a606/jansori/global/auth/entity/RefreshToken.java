package com.a606.jansori.global.auth.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
@Builder
@Entity
public class RefreshToken {

  @Id
  @Column(name = "refresh_token_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "rt_key")
  private String key;

//  @Column(name = "member_id")
//  private Long memberId;

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