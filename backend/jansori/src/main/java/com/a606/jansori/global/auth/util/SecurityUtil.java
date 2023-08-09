package com.a606.jansori.global.auth.util;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

  private final MemberRepository memberRepository;

  private static final String anonymousUser = "anonymousUser";

  public Member getCurrentMemberByToken() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName().equals(anonymousUser)) {
      throw new UnauthorizedException();
    }

    return memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new);
  }

  public Member getNullableMemberByToken() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName().equals(anonymousUser)) {
      return null;
    }

    return memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new);
  }

  public Long getCurrentMemberIdByToken() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName().equals(anonymousUser)) {
      throw new UnauthorizedException();
    }

    return memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new).getId();
  }


}
