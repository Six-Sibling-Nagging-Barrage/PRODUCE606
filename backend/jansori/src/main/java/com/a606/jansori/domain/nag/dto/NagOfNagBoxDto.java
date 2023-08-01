package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagOfNagBoxDto extends NagDto {

  private Boolean unlocked;

  private Integer likeCount;

  @JsonProperty("nagMember")
  private FeedMemberDto feedMemberDto;
}
