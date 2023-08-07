package com.a606.jansori.domain.member.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatchMemberInfoReqDto {

  @NotBlank(message = "닉네임이 공백입니다.")
  @Pattern(regexp = "^(?!\\s)(?!.*\\s{2})[가-힣a-zA-Z0-9_\\s]{1,11}(?<!\\s)$", message = "올바른 형식이 아닙니다.")
  @Length(min = 2, max = 11, message = "닉네임은 2자 이상 11자 이하여야 합니다.")
  private String nickname;

  @Length(max = 200, message = "자기소개는 200자 이하여야 합니다.")
  private String bio;

  @URL(message = "이미지 주소는 URL 형식과 일치해야 합니다.")
  private String imageUrl;

  private List<Long> tags;

}
