package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagOfNagBox extends NagDto {

  private String preview;

  private Boolean unlocked;

  private Integer likeCount;

  private Boolean isLiked;

  @JsonProperty(value = "nagMember")
  private NagMember nagMember;

  public static NagOfNagBox fromNag(Nag nag) {
    return NagOfNagBox.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .preview(nag.getPreview())
        .unlocked(false)
        .likeCount(nag.getLikeCount())
        .isLiked(false)
        .nagMember(NagMember.from(nag.getMember()))
        .build();
  }

  public void changeUnlocked() {
    this.unlocked = !this.unlocked;
  }

  public void changeIsLikedStatus() {
    this.isLiked = !this.isLiked;
  }
}
