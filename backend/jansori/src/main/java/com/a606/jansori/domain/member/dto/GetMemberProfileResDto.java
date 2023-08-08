package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetMemberProfileResDto {

  private Long id;
  private String nickname;
  private String bio;
  private String imageUrl;

  public static GetMemberProfileResDto from(Member member) {
    return GetMemberProfileResDto.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .bio(member.getBio())
        .imageUrl(member.getImageUrl())
        .build();
  }
}
