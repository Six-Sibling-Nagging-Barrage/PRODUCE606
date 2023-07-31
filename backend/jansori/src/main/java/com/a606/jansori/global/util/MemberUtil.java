package com.a606.jansori.global.util;

import com.a606.jansori.global.oauth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberUtil {


    private final HttpSession httpSession;

    public Optional<Long> getSessionMemberId() {
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContext.getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Optional<Long> sessionMemberId = Optional.ofNullable(principalDetails.getId());
        return sessionMemberId;
    }
}
