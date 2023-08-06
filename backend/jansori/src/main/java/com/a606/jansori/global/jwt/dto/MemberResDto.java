package com.a606.jansori.global.jwt.dto;

import com.a606.jansori.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResDto {

  private String email;

  public static MemberResDto of(Member member) {

    return new MemberResDto(member.getEmail());

  }

}
