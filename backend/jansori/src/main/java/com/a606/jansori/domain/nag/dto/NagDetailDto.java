package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.tag.domain.NagTag;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagDetailDto extends NagDto {

  private Integer likeCount;
  private String tagName;
  private LocalDateTime createAt;

  public static NagDetailDto from(NagTag nagTag) {
    return NagDetailDto.builder()
        .content(nagTag.getNag().getContent())
        .likeCount(nagTag.getNag().getLikeCount())
        .tagName(nagTag.getTag().getName())
        .createAt(nagTag.getNag().getCreatedAt())
        .build();
  }
}
