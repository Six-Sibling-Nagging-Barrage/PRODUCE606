package com.a606.jansori.global.oauth;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.oauth.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
//        boolean isMemberClass = Member.class.equals(parameter.getParameterType());
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("annotation" + principal);
        Optional<Member> member = memberRepository.findByOauthIdentifier(principal);
//        //Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//        String id = principal.getId();
        return member;
    }
}