package com.a606.jansori.global.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class OAuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        log.info(request.getScheme());
        log.info(request.getServerName());
        log.info(String.valueOf(request.getServerPort()));

        response.sendRedirect(
                request.getScheme() + "://" +
                        request.getServerName() + ":"
                        + request.getServerPort());

    }
}
