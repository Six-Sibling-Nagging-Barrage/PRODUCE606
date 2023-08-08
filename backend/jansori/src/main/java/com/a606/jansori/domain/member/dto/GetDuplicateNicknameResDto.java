package com.a606.jansori.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetDuplicateNicknameResDto {

  Boolean isDuplicated;

  public static GetDuplicateNicknameResDto from(Boolean isDuplicated) {
    return GetDuplicateNicknameResDto.builder()
        .isDuplicated(isDuplicated)
        .build();
  }
}
