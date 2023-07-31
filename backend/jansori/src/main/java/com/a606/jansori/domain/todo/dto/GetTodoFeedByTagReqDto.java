package com.a606.jansori.domain.todo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetTodoFeedByTagReqDto extends GetTodoFeedByFollowingReqDto {

  @Positive(message = "유효하지 않은 태그 아이디 형식입니다.")
  @NotNull
  private Long tagId;
}
