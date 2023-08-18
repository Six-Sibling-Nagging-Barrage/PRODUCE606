package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.todo.domain.Todo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTodoByDateResDto {

  List<TodoDto> todos = new ArrayList<>();

  public static GetTodoByDateResDto from(List<Todo> todos) {

    return GetTodoByDateResDto.builder()
        .todos(todos.stream()
            .map(TodoDto::from)
            .collect(Collectors.toList()))
        .build();
  }

}
