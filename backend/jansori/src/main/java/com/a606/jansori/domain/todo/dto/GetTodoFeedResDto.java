package com.a606.jansori.domain.todo.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetTodoFeedResDto {

  private List<TodoFeedDto> feed;

  private Boolean hasNext;

  private Long nextCursor;
}
