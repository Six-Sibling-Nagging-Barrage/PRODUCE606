package com.a606.jansori.domain.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class GetTodoFeedResDto {

    private Slice<FeedDto> feeds;
}
