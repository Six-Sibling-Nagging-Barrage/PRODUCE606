package com.a606.jansori.domain.tag.dto;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.validator.TagName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTagReqDto {

  @TagName
  private String keyword;
}
