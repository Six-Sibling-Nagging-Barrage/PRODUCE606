package com.a606.jansori.domain.member.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.dto.TagDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetUserProfileResDto {

  private Long id;

  private String nickname;

  private String bio;

  private String imageUrl;

  public static GetUserProfileResDto from(Member member) {
    return GetUserProfileResDto.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .bio(member.getBio())
        .imageUrl(member.getImageUrl())
        .build();
  }
}
