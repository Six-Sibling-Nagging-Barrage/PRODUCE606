package com.a606.jansori.domain.nag.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetNagsOfNagBoxResDto {

  private List<NagOfNagBox> nags;

  public static GetNagsOfNagBoxResDto fromNagsOfNagBox(List<NagOfNagBox> nags) {
    return GetNagsOfNagBoxResDto.builder()
        .nags(nags)
        .build();
  }
}
