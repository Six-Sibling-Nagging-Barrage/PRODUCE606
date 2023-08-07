package com.a606.jansori.domain.todo.dto;

import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTodoFeedByFollowingReqDto {

  private Long cursor;

  @Positive(message = "유효하지 않은 페이지 크기입니다.")
  private Integer size;

  public GetTodoFeedByFollowingReqDto() {
    this.size = 10;
  }
}
