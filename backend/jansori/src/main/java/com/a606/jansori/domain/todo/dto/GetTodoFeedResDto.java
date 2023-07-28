package com.a606.jansori.domain.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetTodoFeedResDto {

    private List<FeedDto> feed;

    private Boolean hasNext;

    private Long nextCursor;
}
