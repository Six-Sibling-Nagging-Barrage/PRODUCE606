package com.a606.jansori.global.config;

import com.a606.jansori.global.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final OAuthService oAuthService;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOriginPattern("*");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.httpBasic().disable()
        .cors().configurationSource(corsConfigurationSource())

        .and()
        .authorizeRequests()
        .antMatchers("/register").hasRole("GUEST")
        .anyRequest().permitAll()

        .and()
        .csrf().disable()
        .headers().frameOptions().disable()

        .and()
        .logout().logoutSuccessUrl("/") //logout 요청시 홈으로 이동 - 기본 logout url = "/logout"

        .and()
        .oauth2Login()
        .defaultSuccessUrl("/", true) //OAuth2 성공시 redirect
        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
        .userService(oAuthService);

    //OAuth2 로그인 성공 시, 작업을 진행할 MemberService
    return http.build();
  }

}