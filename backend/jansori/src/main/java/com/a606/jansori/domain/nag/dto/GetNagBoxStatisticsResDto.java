package com.a606.jansori.domain.nag.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetNagBoxStatisticsResDto {

  private Long totalMemberCount;

  private Long totalDoneTodoCount;

  private Long totalNagsCount;

  public static GetNagBoxStatisticsResDto of(
      Long totalMemberCount,
      Long totalDoneTodoCount,
      Long totalNagsCount) {
    return GetNagBoxStatisticsResDto.builder()
        .totalMemberCount(totalMemberCount)
        .totalDoneTodoCount(totalDoneTodoCount)
        .totalNagsCount(totalNagsCount)
        .build();
  }
}
