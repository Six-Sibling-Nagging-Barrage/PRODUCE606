package com.a606.jansori.global.jwt.dto;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDto {

  private String email;

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
