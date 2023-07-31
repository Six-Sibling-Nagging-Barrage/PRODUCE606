package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMyProfileResDto {

  private Long id;
  private String nickname;
  private String bio;
  private String imageUrl;
  private Long ticket;

  public static GetMyProfileResDto from(Member member) {
    return GetMyProfileResDto.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .bio(member.getBio())
        .imageUrl(member.getImageUrl())
        .ticket(member.getTicket())
        .build();
  }
}
