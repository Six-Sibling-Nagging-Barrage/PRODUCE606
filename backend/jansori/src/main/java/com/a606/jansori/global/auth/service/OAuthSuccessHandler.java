package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.dto.GetLoginSuccessResDto;
import com.a606.jansori.global.auth.dto.PrincipalDetails;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        MemberRole role = principalDetails.getRole();
        Long memberId = principalDetails.getId();
        Boolean isRegistered = role != MemberRole.GUEST;

//        String uri = "http://i9a606.p.ssafy.io/";
        String uri = "http://localhost:3000/";

        if (role == MemberRole.GUEST) {
            isRegistered = false;

//            uri = "http://i9a606.p.ssafy.io/signup";
            uri = "http://localhost:3000/signup";
        } else if (savedRequest != null) {
            uri = savedRequest.getRedirectUrl();
        }

//        String json = objectMapper.writeValueAsString(
//                EnvelopeResponse.<GetLoginSuccessResDto>builder()
//                        .data(GetLoginSuccessResDto.builder()
//                                .id(memberId)
//                                .isRegistered(isRegistered).
//                                build()));
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setCharacterEncoding("utf-8");
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(json);

        response.sendRedirect(uri);

    }

}
