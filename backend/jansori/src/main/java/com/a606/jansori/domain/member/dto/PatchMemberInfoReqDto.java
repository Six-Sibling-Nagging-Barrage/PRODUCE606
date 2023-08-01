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
  @Pattern(regexp = "/(^\\s|\\s$|\\s{2,}|[^\\w가-힣])/", message = "한글, 영문, 숫자, _, 띄어쓰기만 사용할 수 있으며," +
          "띄어쓰기를 연달아 사용하거나, 처음과 마지막에 사용할 수 없습니다.")
  @Length(min=1, max = 11, message = "닉네임은 1자 이상 11자 이하여야 합니다.")
  private String nickname;

  @Length(max = 200, message = "자기소개는 200자 이하여야 합니다.")
  private String bio;

  @URL(message = "이미지 주소는 URL 형식과 일치해야 합니다.")
  private String imageUrl;

  private List<Long> tags;

}
