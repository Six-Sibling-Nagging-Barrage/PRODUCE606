package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.tag.domain.Tag;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagDetailDto extends NagDto {

  private Integer likeCount;
  private String tagName;

  public static NagDetailDto from(Nag nag, Tag tag) {
    return NagDetailDto.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .likeCount(nag.getLikeCount())
        .tagName(tag.getName())
        .build();
  }
}
