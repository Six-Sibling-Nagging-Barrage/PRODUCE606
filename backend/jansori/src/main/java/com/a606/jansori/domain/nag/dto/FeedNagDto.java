package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.domain.Nag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FeedNagDto {

  private Long nagId;

  @JsonProperty("nagMember")
  private FeedMemberDto feedMemberDto;

  private Boolean unlocked;

  private Boolean isLiked;

  private String content;

  private Integer likeCount;


  public static FeedNagDto fromNagAndUnlockedAndIsLiked(Nag nag, Boolean isUnlocked, Boolean isLiked) {

    return FeedNagDto.builder()
        .nagId(nag.getId())
        .feedMemberDto(FeedMemberDto.from(nag.getMember()))
        .likeCount(nag.getLikeCount())
        .content(getNagContentByUnlocked(isUnlocked, nag))
        .unlocked(isUnlocked)
        .isLiked(isLiked)
        .build();
  }

  private static String getNagContentByUnlocked(Boolean unlocked, Nag nag) {

    return unlocked ? nag.getContent() : nag.getPreview();
  }

  public void changeUnlocked(Boolean unlocked, Nag nag) {
    this.unlocked = unlocked;
    if (this.unlocked) {
      this.content = nag.getContent();
    }
  }

  public void changeIsLiked(Boolean isLiked) {
    this.isLiked = isLiked;
  }
}
