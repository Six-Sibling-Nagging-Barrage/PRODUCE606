package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetNagOfMainPageResDto {

  private List<NagDto> nags;

  public static GetNagOfMainPageResDto from(List<NagDto> nags) {
    return GetNagOfMainPageResDto.builder()
        .nags(nags)
        .build();
  }
}
