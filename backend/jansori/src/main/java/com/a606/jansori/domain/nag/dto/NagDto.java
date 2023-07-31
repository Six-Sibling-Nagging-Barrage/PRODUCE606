package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagDto {

  private Long nagId;
  private String content;
  private LocalDateTime createAt;

  public static NagDto from(Nag nag) {
    return NagDto.builder()
        .nagId(nag.getId())
        .content(nag.getContent())
        .createAt(nag.getCreatedAt())
        .build();
  }
}