package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PatchMemberInfoResDto {

  private Long memberId;
  private String nickname;
  private String bio;
  private String imageUrl;
  private List<Long> tags;

  public static PatchMemberInfoResDto of(Member member
      , List<Long> tags) {
    return PatchMemberInfoResDto.builder()
        .memberId(member.getId())
        .nickname(member.getNickname())
        .bio(member.getBio())
        .imageUrl(member.getImageUrl())
        .tags(tags)
        .build();
  }

}
