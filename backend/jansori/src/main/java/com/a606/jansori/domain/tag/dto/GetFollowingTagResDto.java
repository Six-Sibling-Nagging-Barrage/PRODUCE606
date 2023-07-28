package com.a606.jansori.domain.tag.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetFollowingTagResDto {

  private List<TagElement> tags;

  public static GetFollowingTagResDto from(List<TagElement> tags) {
    return GetFollowingTagResDto.builder()
        .tags(tags)
        .build();
  }
}
