package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagInteraction;
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

  public static NagOfNagBox from(Nag nag) {
    return NagOfNagBox.builder()
        .nagId(nagInteraction.getNag().getId())
        .content(nagInteraction.getNag().getContent())
        .createAt(nagInteraction.getNag().getCreatedAt())
        .preview(nagInteraction.getNag().getPreview())
        .unlocked(nagInteraction.getNagUnlock())
        .likeCount(nagInteraction.getNag().getLikeCount())
        .isLiked(nagInteraction.getNagLike())
        .nagMember(NagMember.from(nagInteraction.getNag().getMember()))
        .build();
  }

  public void changeStatus(NagInteraction nagInteraction) {
    unlocked = nagInteraction.getNagUnlock();
    isLiked = nagInteraction.getNagLike();
  }
}
