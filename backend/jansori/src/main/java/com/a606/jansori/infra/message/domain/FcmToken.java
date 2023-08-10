package com.a606.jansori.infra.message.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "fcm_token")
public class FcmToken extends BaseTimeEntity {

  @Id
  @Column(name = "fcm_token_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  private String fcmToken;

  public FcmToken(Member member, String fcmToken) {
    this.member = member;
    this.fcmToken = fcmToken;
  }

}
