package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDto {

    private Long todoId;

    private Boolean display;

    private Boolean finished;

    private String content;

    private List<TagDto> tags = new ArrayList<>();

    public static TodoDto from(Todo todo) {

        return TodoDto.builder()
                .todoId(todo.getId())
                .display(todo.getDisplay())
                .finished(todo.getFinished())
                .content(todo.getContent())
                .tags(todo.getTodoTags().stream()
                        .map(todoTag -> TagDto.from(todoTag))
                        .collect(Collectors.toList()))
                .build();
    }

}
