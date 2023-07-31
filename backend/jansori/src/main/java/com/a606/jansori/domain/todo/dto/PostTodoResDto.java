package com.a606.jansori.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostTodoResDto {

  @JsonProperty("todo")
  private TodoDto todoDto;
}
