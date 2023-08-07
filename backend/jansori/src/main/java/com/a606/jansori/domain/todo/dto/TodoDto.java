package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.tag.dto.TagDto;
import com.a606.jansori.domain.todo.domain.Todo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class TodoDto {

  private Long todoId;

  private Boolean finished;

  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate todoAt;

  private List<TagDto> tags;

  protected TodoDto(Todo todo) {

    this.todoId = todo.getId();
    this.finished = todo.getFinished();
    this.content = todo.getContent();
    this.todoAt = todo.getTodoAt();
    this.tags = todo.getTodoTags().stream()
        .map(TagDto::from)
        .collect(Collectors.toList());
  }

  public static TodoDto from(Todo todo) {

    return new TodoDto(todo);
  }

}
