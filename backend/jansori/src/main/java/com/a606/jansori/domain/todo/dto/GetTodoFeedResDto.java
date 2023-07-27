package com.a606.jansori.domain.todo.dto;

import com.a606.jansori.domain.todo.domain.Todo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class GetTodoFeedResDto {

    private List<FeedDto> feed;

    private Boolean hasNext;

    private Long nextCursor;

    public static GetTodoFeedResDto fromPagedFeedDto(Slice<Todo> todos) {

        Integer size = todos.getSize() - 1;

        return GetTodoFeedResDto.builder()
                .feed(todos.getContent().stream()
                        .limit(size)
                        .map(todo -> FeedDto.fromTodo(todo))
                        .collect(Collectors.toList()))
                .hasNext(todos.getContent().get(size) == null)
                .nextCursor(todos.getContent().get(size).getId())
                .build();
    }
}
