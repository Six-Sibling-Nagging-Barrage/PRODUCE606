package com.a606.jansori.global.auth.util;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.dto.PrincipalDetails;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

  private final HttpSession httpSession;
  private final MemberRepository memberRepository;

  public Long getSessionMemberId() {

    SecurityContext securityContext = (SecurityContext) httpSession.
        getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

    Authentication authentication = securityContext.getAuthentication();
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

    Long memberId = principalDetails.getId();

    return memberId;
  }

  public Member getMemberFromSession() {

    return memberRepository.findById(getSessionMemberId())
        .orElseThrow(MemberNotFoundException::new);
  }

  public Member getNullableMemberFromSession() {

    return memberRepository.findById(getSessionMemberId()).orElseGet(null);
  }

  public Member getTestMemberFromSession() {

    return memberRepository.findById(1L).orElseGet(null);
  }
}
