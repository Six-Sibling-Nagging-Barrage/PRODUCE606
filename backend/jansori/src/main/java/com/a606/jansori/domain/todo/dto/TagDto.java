package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.domain.TodoTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TagDto {

  private Long tagId;

  private String tagName;

  public static TagDto from(TodoTag todoTag) {

    return TagDto.builder()
        .tagId(todoTag.getTag().getId())
        .tagName(todoTag.getTag().getName())
        .build();
  }

  public static TagDto from(TagFollow tagFollow) {
    return TagDto.builder()
        .tagId(tagFollow.getTag().getId())
        .tagName(tagFollow.getTag().getName())
        .build();
  }

  public static TagDto from(Tag tag) {
    return TagDto.builder()
        .tagId(tag.getId())
        .tagName(tag.getName())
        .build();
  }

  public Tag toNewTag() {

    return Tag.builder()
        .name(this.tagName)
        .build();
  }
}
