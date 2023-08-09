package com.a606.jansori.global.config;

import com.a606.jansori.global.auth.handler.JwtAccessDeniedHandler;
import com.a606.jansori.global.auth.handler.JwtAuthenticationEntryPoint;
import com.a606.jansori.global.auth.util.TokenProvider;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(
        List.of("http://localhost:3000", "http://i9a606.p.ssafy.io", "https://i9a606.p.ssafy.io"));

    configuration.setAllowedMethods(
        List.of("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));

    configuration.setAllowedHeaders(
        List.of("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin",
            "Access-Control-Request-Method", "Access-Control-Request-Headers"));

    configuration.setExposedHeaders(
        List.of("X-Get-Header", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
            "Content-Type", "Authorization"));

    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .formLogin().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .cors().configurationSource(corsConfigurationSource())

        .and()
        .authorizeRequests()
        .antMatchers("/login/**").permitAll()
        .antMatchers("/exception/**").permitAll()
        .antMatchers("/signup/**").permitAll()
        .antMatchers("/auth/**").permitAll()
        .antMatchers("/storage/**").permitAll()
        .antMatchers("/nags/main-page").permitAll()
        .anyRequest().authenticated()

        .and()
        .csrf().disable()
        .headers().frameOptions().disable()

        .and()
        .logout()
        .logoutUrl("/logout")

        .and().apply(new JwtSecurityConfig(tokenProvider));

    return http.build();

  }

}