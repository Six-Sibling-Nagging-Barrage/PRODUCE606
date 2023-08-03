package com.a606.jansori.domain.tag.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GetAutoCompleteTagsResDto {

  private Integer tagCount;

  private List<TagDto> tags;

  public static GetAutoCompleteTagsResDto fromTagsBySearch(List<TagDto> tags) {
    return GetAutoCompleteTagsResDto.builder()
        .tagCount(tags.size())
        .tags(tags)
        .build();
  }
}
