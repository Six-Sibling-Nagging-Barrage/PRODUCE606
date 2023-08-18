package com.a606.jansori.global.auth.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
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

  private String email;

  private MemberRole memberRole;

  private String nickname;

  private String imageUrl;

  private Long ticket;

  private Boolean isUnreadNotificationLeft;

  public void of(Member member, boolean isUnreadNotificationLeft) {
    this.memberId = member.getId();
    this.email = member.getEmail();
    this.memberRole = member.getMemberRole();
    this.nickname = member.getNickname();
    this.imageUrl = member.getImageUrl();
    this.ticket = member.getTicket();
    this.isUnreadNotificationLeft = isUnreadNotificationLeft;
  }

  public void from(Member member) {
    this.memberId = member.getId();
    this.email = member.getEmail();
    this.memberRole = member.getMemberRole();
    this.nickname = member.getNickname();
    this.imageUrl = member.getImageUrl();
    this.ticket = member.getTicket();
    this.isUnreadNotificationLeft = null;
  }

}
