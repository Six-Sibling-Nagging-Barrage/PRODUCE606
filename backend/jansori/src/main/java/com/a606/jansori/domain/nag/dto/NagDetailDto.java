package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.dto.TagDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagDetailDto extends NagDto {

  private Integer likeCount;
  private Integer deliveredCount;
  private TagDto tag;

  public static NagDetailDto ofNagAndTag(Nag nag, Tag tag) {
    return NagDetailDto.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .likeCount(nag.getLikeCount())
        .tag(TagDto.from(tag))
        .deliveredCount(nag.getTodos().size())
        .build();
  }
}
