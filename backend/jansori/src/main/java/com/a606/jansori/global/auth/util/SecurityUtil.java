package com.a606.jansori.global.auth.util;

import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.global.auth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    private final HttpSession httpSession;

    public Object getSessionMemberId() {

        SecurityContext securityContext = (SecurityContext) httpSession.
            getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        Authentication authentication = securityContext.getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        Long memberId = principalDetails.getId();

        if (memberId == null){
            return new MemberNotFoundException();
        }

        return memberId;
    }
}
