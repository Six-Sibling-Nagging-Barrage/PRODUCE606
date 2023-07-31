package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagResDto {

  private List<NagDetailDto> nags;

  public static GetNagResDto of(List<NagDetailDto> nags) {
    return GetNagResDto.builder()
        .nags(nags)
        .build();
  }
}
