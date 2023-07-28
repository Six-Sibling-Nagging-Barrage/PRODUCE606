package com.a606.jansori.domain.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetDuplicateNicknameReqDto {

  @NotNull
  @Size(min = 1, max = 11)
  private String nickname;

}
