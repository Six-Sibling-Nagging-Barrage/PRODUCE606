package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NagMember {

  private Long memberId;

  private String nickname;

  private String imageUrl;

  public static NagMember from(Member member) {
    return NagMember.builder()
        .memberId(member.getId())
        .nickname(member.getNickname())
        .imageUrl(member.getImageUrl())
        .build();
  }
}
