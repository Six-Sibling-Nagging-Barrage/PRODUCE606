package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagsOfOtherResDto {

  List<NagOfProfileDto> nags;

  private Boolean hasNext;

  private Long nextCursor;

  public static GetNagsOfOtherResDto ofOtherNagsList(
      List<NagOfProfileDto> nags,
      Boolean hasNext,
      Long nextCursor) {
    return GetNagsOfOtherResDto.builder()
        .nags(nags)
        .hasNext(hasNext)
        .nextCursor(nextCursor)
        .build();
  }
}
