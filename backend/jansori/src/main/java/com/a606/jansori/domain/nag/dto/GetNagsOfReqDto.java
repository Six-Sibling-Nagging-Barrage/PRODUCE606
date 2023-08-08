package com.a606.jansori.domain.nag.dto;

import javax.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetNagsOfReqDto {

  private Long cursor;

  @Positive(message = "유효하지 않은 페이지 크기입니다.")
  private Integer size;

  public GetNagsOfReqDto() {
    this.size = 10;
  }
}
