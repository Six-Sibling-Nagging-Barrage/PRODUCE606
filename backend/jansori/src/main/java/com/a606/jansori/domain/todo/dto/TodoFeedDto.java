package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.domain.NagInteraction;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TodoFeedDto extends TodoDto{

  @JsonProperty("member")
  private FeedMemberDto feedMemberDto;

  @JsonProperty("nag")
  private FeedNagDto feedNagDto;

  protected TodoFeedDto(Todo todo, Boolean unlocked, Boolean isLiked) {

    super(todo);

    this.feedMemberDto = FeedMemberDto.from(todo.getMember());

    if(todo.getNag() != null) {
      this.feedNagDto = FeedNagDto.fromNagAndUnlockedAndIsLiked(todo.getNag(), unlocked, isLiked);
    }
  }

  public static TodoFeedDto from(Todo todo, Boolean unlocked, Boolean isLiked) {

    return new TodoFeedDto(todo, unlocked, isLiked);
  }

  public void changeNagLikedAndUnlockStatus(NagInteraction nagInteraction) {
    feedNagDto.changeUnlocked(nagInteraction.getNagUnlock(), nagInteraction.getNag());
    feedNagDto.changeIsLiked(nagInteraction.getNagLike());
  }
}
