package com.a606.jansori.global.auth.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthSignupResDto {

  private String email;

  public static AuthSignupResDto from(Member member) {

    return new AuthSignupResDto(member.getEmail());

  }

}
