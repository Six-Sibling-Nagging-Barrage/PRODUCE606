package com.a606.jansori.domain.tag.dto;

import com.a606.jansori.domain.tag.domain.TagFollow;
import lombok.Getter;


@Getter
public class TagElement {

  private Long tagId;

  private String tagName;

  public TagElement(TagFollow tagFollow) {
    this.tagId = tagFollow.getTag().getId();
    this.tagName = tagFollow.getTag().getName();
  }
}
