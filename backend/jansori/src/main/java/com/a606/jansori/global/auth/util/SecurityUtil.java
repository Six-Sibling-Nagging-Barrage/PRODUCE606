package com.a606.jansori.global.auth.util;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.exception.AuthMemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityUtil {

  private final MemberRepository memberRepository;

  public Member getCurrentMemberByToken() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new AuthMemberNotFoundException();
    }

    return memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new);
  }

  public Member getNullableMemberByToken() {

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      return null;
    }

    return memberRepository.findMemberByEmail(authentication.getName()).orElse(null);
  }

  public Long getCurrentMemberIdByToken(){
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new AuthMemberNotFoundException();
    }

    Member member = memberRepository.findMemberByEmail(authentication.getName())
            .orElseThrow(MemberNotFoundException::new);
    return member.getId();

  }


}
