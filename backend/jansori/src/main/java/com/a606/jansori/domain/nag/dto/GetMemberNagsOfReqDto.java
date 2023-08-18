package com.a606.jansori.domain.nag.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMemberNagsOfReqDto extends GetNagsOfReqDto {

  private Long memberId;

  public GetMemberNagsOfReqDto() {
    super();
  }
}
