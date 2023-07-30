package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.tag.domain.NagTag;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class NagDto {

  private String content;
  private Integer likeCount;
  private String tagName;
  private LocalDateTime createAt;

  public NagDto(NagTag nagTag) {
    this.content = nagTag.getNag().getContent();
    this.likeCount = nagTag.getNag().getLikeCount();
    this.tagName = nagTag.getTag().getName();
    this.createAt = nagTag.getNag().getCreatedAt();
  }
}
