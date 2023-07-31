package com.a606.jansori.domain.todo.dto;

import java.time.LocalDate;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class GetTodoByDateReqDto {

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;

}
