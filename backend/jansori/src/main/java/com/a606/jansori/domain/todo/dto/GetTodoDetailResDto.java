package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.persona.dto.FeedTodoPersonaDetailDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class GetTodoDetailResDto extends TodoFeedDto {

  @JsonProperty("personas")
  private List<FeedTodoPersonaDetailDto> feedTodoPersonaDetailDtos;

  private GetTodoDetailResDto(Todo todo, Boolean unlocked, Boolean isLiked) {

    super(todo, unlocked, isLiked);

    this.feedTodoPersonaDetailDtos = todo.getTodoPersonas().stream()
        .map(FeedTodoPersonaDetailDto::from)
        .collect(Collectors.toList());
  }

  public static GetTodoDetailResDto from(Todo todo, Boolean unlocked, Boolean isLiked) {

    return new GetTodoDetailResDto(todo, unlocked, isLiked);
  }
}
