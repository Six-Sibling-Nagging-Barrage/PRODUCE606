package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagsOfResDto {

  private List<NagDetailDto> nags;

  private Boolean hasNext;

  private Long nextCursor;

  public static GetNagsOfResDto ofNagList(List<NagDetailDto> nags, Boolean hasNext,
      Long nextCursor) {
    return GetNagsOfResDto.builder()
        .nags(nags)
        .hasNext(hasNext)
        .nextCursor(nextCursor)
        .build();
  }
}
