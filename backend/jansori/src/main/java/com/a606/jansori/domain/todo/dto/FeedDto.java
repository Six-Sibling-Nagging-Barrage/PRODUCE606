package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.member.dto.FeedMemberDto;
import com.a606.jansori.domain.nag.dto.FeedNagDto;
import com.a606.jansori.domain.persona.dto.FeedLineDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedDto {

  @JsonProperty("todoMember")
  private FeedMemberDto feedMemberDto;

  @JsonProperty("todo")
  private TodoDto todoDto;

  @JsonProperty("nag")
  private FeedNagDto feedNagDto;

  @JsonProperty("persona")
  private List<FeedLineDto> lines;

  public static FeedDto ofFeedRelatedDto(FeedMemberDto feedMemberDto, Todo todo,
      FeedNagDto feedNagDto) {

    return FeedDto.builder()
        .feedMemberDto(feedMemberDto)
        .todoDto(TodoDto.from(todo))
        .feedNagDto(feedNagDto)
        .lines(todo.getTodoPersonas().stream()
            .map(FeedLineDto::from)
            .collect(Collectors.toList()))
        .build();
  }

}
