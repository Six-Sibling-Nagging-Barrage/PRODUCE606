package com.a606.jansori.domain.member.dto;

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

}
