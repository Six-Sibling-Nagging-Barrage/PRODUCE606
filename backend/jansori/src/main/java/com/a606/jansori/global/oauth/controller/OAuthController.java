package com.a606.jansori.global.oauth.controller;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth")
public class OAuthController {
    private final HttpSession httpSession;
    private final MemberRepository memberRepository;


    @GetMapping("/loginInfo")
    public String oauthLoginInfo(Authentication authentication) {
        // 방법 1 : 파라미터로 Authentication을 받는 방법
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String identifier = oAuth2User.getName(); // 로그인한 유저의 식별자
        Optional<Member> member = memberRepository.findByOauthIdentifier(identifier);

        // 방법 2 : SecurityContextHolder에 접근하는 방법
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication1 = securityContext.getAuthentication();
        String principal = authentication1.getName();
        System.out.println(principal);

        // 인증된 유저 정보 출력
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return attributes.toString();

    }
}
