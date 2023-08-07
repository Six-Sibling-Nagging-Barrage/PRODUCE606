package com.a606.jansori.global.auth.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqDto {

  @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
  @NotBlank(message = "이메일은 필수 입력 값입니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
  @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
      message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
  private String password;

  public Member toMember(PasswordEncoder passwordEncoder) {

    return Member.builder()
        .email(email)
        .password(passwordEncoder.encode(password))
        .memberRole(MemberRole.GUEST)
        .build();

  }

  public UsernamePasswordAuthenticationToken toAuthentication() {

    return new UsernamePasswordAuthenticationToken(email, password);

  }

}
