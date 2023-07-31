package com.a606.jansori.domain.nag.dto;

import com.a606.jansori.domain.nag.domain.Nag;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagResDto {

  private List<NagDto> nags;

  public static GetNagResDto of(List<NagDto> nags) {
    return GetNagResDto.builder()
        .nags(nags)
        .build();
  }
}
