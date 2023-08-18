package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TodoCacheDto {

  private Long todoId;

  private List<Long> tagIds;

  public static TodoCacheDto from(Todo todo) {
    return TodoCacheDto.builder()
        .todoId(todo.getId())
        .tagIds(todo.getTodoTags()
            .stream()
            .map(tt -> tt.getTag().getId())
            .collect(Collectors.toList()))
        .build();
  }

}
