package com.a606.jansori.domain.todo.dto;

import java.time.YearMonth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class GetTodoMonthlyExistenceReqDto {

  @DateTimeFormat(pattern = "yyyy-MM")
  private YearMonth yearMonth;

}
