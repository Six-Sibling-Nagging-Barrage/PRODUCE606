package com.a606.jansori.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchTodoResDto {

  private Boolean isAccomplished;

  public static PatchTodoResDto from(Boolean isAccomplished) {
    return new PatchTodoResDto(isAccomplished);
  }

}
