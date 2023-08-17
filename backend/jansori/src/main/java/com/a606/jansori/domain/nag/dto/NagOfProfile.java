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
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .likeCount(nag.getLikeCount())
        .tag(TagDto.from(nag.getTag()))
        .deliveredCount(nag.getTodos().size())
        .preview(nag.getPreview())
        .unlocked(false)
        .isLiked(false)
        .build();
  }

  public void changeStatus(NagInteraction nagInteraction) {
    unlocked = nagInteraction.getNagUnlock();
    isLiked = nagInteraction.getNagLike();
  }
}
