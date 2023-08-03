package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.global.auth.dto.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
    , Authentication authentication) throws IOException, ServletException{

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        MemberRole role = principalDetails.getRole();

        String uri = "http://i9a606.p.ssafy.io/";

        if(role == MemberRole.GUEST){
            uri = "http://i9a606.p.ssafy.io/signup";
        } else if (savedRequest != null ){
            uri = savedRequest.getRedirectUrl();
        }

        log.info(uri);

        response.sendRedirect(uri);

    }

}
