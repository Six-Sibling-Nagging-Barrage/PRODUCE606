package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FeedTodoDto extends TodoDto{

  @JsonProperty("member")
  private FeedMemberDto feedMemberDto;

  @JsonProperty("nag")
  private FeedNagDto feedNagDto;

  protected FeedTodoDto(Todo todo, Boolean unlocked, Boolean isLiked) {

    super(todo);

    this.feedMemberDto = FeedMemberDto.from(todo.getMember());
    this.feedNagDto = FeedNagDto.fromNagAndUnlockedAndIsLiked(todo.getNag(), unlocked, isLiked);

  }

  public static FeedTodoDto from(Todo todo, Boolean unlocked, Boolean isLiked) {

    return new FeedTodoDto(todo, unlocked, isLiked);
  }
}
