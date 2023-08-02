package com.a606.jansori.domain.nag.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class NagOfNagBox extends NagDto {

  private Boolean unlocked;

  private Integer likeCount;
}
