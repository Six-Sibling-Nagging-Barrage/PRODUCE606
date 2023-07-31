package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagOfProfilePageResDto {

  private List<NagDetailDto> nags;

  public static GetNagOfProfilePageResDto from(List<NagDetailDto> nags) {
    return GetNagOfProfilePageResDto.builder()
        .nags(nags)
        .build();
  }
}
