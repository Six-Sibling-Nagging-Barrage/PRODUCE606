package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagInteraction;
import com.a606.jansori.domain.tag.dto.TagDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagOfProfile extends NagDetailDto {

  private String preview;
  private Boolean unlocked;
  private Boolean isLiked;

  public static NagOfProfile from(Nag nag) {
    return NagOfProfile.builder()
        .nagId(nagInteraction.getNag().getId())
        .content(nagInteraction.getNag().getContent())
        .createAt(nagInteraction.getNag().getCreatedAt())
        .likeCount(nagInteraction.getNag().getLikeCount())
        .tag(TagDto.from(nagInteraction.getNag().getTag()))
        .deliveredCount(nagInteraction.getNag().getTodos().size())
        .preview(nagInteraction.getNag().getPreview())
        .unlocked(nagInteraction.getNagUnlock())
        .isLiked(nagInteraction.getNagLike())
        .build();
  }

  public void changeStatus(NagInteraction nagInteraction) {
    unlocked = nagInteraction.getNagUnlock();
    isLiked = nagInteraction.getNagLike();
  }
}
