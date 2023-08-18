package com.a606.jansori.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class GetTodoMonthlyExistenceResDto {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private List<LocalDate> dates;

  public GetTodoMonthlyExistenceResDto(List<LocalDate> dates) {
    this.dates = dates;
  }
}
