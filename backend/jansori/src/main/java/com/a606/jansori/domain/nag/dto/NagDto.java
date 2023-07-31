package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagDto {

  private String content;

  public static NagDto from(Nag nag) {
    return NagDto.builder()
        .content(nag.getContent())
        .build();
  }
}
