package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.tag.domain.Tag;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagOfProfileDto extends NagDetailDto {

  private String preview;
  private Boolean unlocked;

  public static NagOfProfileDto ofNagAndTagAndNagUnlock(Nag nag, Tag tag) {
    return NagOfProfileDto.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .likeCount(nag.getLikeCount())
        .tagName(tag.getName())
        .deliveredCount(nag.getTodos().size())
        .preview(nag.getPreview())
        .unlocked(nag.getNagUnlocks().size() > 0)
        .build();
  }
}
