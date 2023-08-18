package com.a606.jansori.domain.todo.dto;

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
public class PostTodoResDto {

  @JsonProperty("todo")
  private TodoDto todoDto;

  public static PostTodoResDto from(Todo todo) {

    return PostTodoResDto.builder()
        .todoDto(TodoDto.from(todo))
        .build();
  }
}
