package com.a606.jansori.domain.todo.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class GetTodoMonthlyExistenceResDto {

  private List<LocalDate> dates;

  public GetTodoMonthlyExistenceResDto(List<LocalDate> dates) {
    this.dates = dates;
  }
}
