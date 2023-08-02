package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.persona.dto.FeedTodoPersonaDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TodoDetailDto extends FeedTodoDto{

  @JsonProperty("personas")
  private List<FeedTodoPersonaDto> feedTodoPersonaDtos;

  protected TodoDetailDto(Todo todo, Boolean unlocked, Boolean isLiked) {

    super(todo, unlocked, isLiked);

    this.feedTodoPersonaDtos = todo.getTodoPersonas().stream()
        .map(FeedTodoPersonaDto::from)
        .collect(Collectors.toList());
  }
}
