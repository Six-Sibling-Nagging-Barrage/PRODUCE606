package com.a606.jansori.domain.nag.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostNagLikeResDto {

  private boolean liked;

  public static PostNagLikeResDto from(boolean liked) {
    return PostNagLikeResDto.builder()
        .liked(liked)
        .build();
  }
}
