package com.a606.jansori.global.jwt.util;

import com.a606.jansori.global.jwt.entity.PrincipalDetails;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

  private final HttpSession httpSession;

  public Long getSessionMemberId() {

    SecurityContext securityContext = (SecurityContext) httpSession.
        getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

    Authentication authentication = securityContext.getAuthentication();
    PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

    Long memberId = principalDetails.getId();

    return memberId;
  }

  public static Long getCurrentMemberId() {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
    }

    return Long.parseLong(authentication.getName());
  }

}
