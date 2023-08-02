package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedDto {

  @JsonProperty("todoMember")
  private FeedMemberDto feedMemberDto;

  @JsonProperty("todo")
  private TodoDto todoDto;

  @JsonProperty("nag")
  private FeedNagDto feedNagDto;

  public static FeedDto from(Todo todo, FeedNagDto feedNagDto) {

    return FeedDto.builder()
        .feedMemberDto(FeedMemberDto.from(todo.getMember()))
        .todoDto(TodoDto.from(todo))
        .feedNagDto(feedNagDto)
        .build();
  }

}
