package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.persona.dto.FeedTodoPersonaDetailDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetLineDetailsResDto {

  @JsonProperty("todoPersonas")
  private List<FeedTodoPersonaDetailDto> feedTodoPersonaDetailDtos;

  public static GetLineDetailsResDto fromTodoPersonas(List<TodoPersona> todoPersonas) {

    return new GetLineDetailsResDto(todoPersonas.stream()
        .map(FeedTodoPersonaDetailDto::from)
        .collect(Collectors.toList()));
  }
}
