package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.persona.dto.FeedTodoPersonaDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoDto {

  private Long todoId;

  private Boolean display;

  private Boolean finished;

  private String content;

  private LocalDateTime createdAt;

  private List<TagDto> tags;

  @JsonProperty("personas")
  private List<FeedTodoPersonaDto> feedTodoPersonaDtos;

  public static TodoDto from(Todo todo) {

    return TodoDto.builder()
        .todoId(todo.getId())
        .display(todo.getDisplay())
        .finished(todo.getFinished())
        .content(todo.getContent())
        .createdAt(todo.getCreatedAt())
        .tags(todo.getTodoTags().stream()
            .map(todoTag -> TagDto.from(todoTag))
            .collect(Collectors.toList()))
        .feedTodoPersonaDtos(todo.getTodoPersonas().stream()
            .map(FeedTodoPersonaDto::from)
            .collect(Collectors.toList()))
        .build();
  }

}
