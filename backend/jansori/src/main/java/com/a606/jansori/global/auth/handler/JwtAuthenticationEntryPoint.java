package com.a606.jansori.global.auth.handler;

import com.a606.jansori.global.auth.exception.AuthUnauthorizedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      org.springframework.security.core.AuthenticationException authException) {

    throw new AuthUnauthorizedException();

  }

}
