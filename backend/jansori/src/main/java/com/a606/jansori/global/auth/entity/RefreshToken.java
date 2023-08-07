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

  @Column(name = "refreshtoken_email")
  private String email;

  @Column(name = "refreshtoken_value")
  private String value;


  public RefreshToken(String email, String value) {
    this.email = email;
    this.value = value;
  }

  public RefreshToken updateValue(String token) {
    this.value = token;
    return this;
  }
}