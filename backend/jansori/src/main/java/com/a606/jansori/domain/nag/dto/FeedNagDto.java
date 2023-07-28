package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.domain.Nag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedNagDto {

  @JsonProperty("nagMember")
  private FeedMemberDto feedMemberDto;

  private Boolean unlocked;

  private String content;

  private Long likeCount;

  public static FeedNagDto ofMemberAndLikeCount(Member member, Long likeCount) {

    return FeedNagDto.builder()
        .feedMemberDto(FeedMemberDto.from(member))
        .likeCount(likeCount)
        .build();
  }

  public void setNagContentByUnlocked(Boolean unlocked, Nag nag) {

    this.content = unlocked ? nag.getContent() : nag.getPreview();
  }

}
