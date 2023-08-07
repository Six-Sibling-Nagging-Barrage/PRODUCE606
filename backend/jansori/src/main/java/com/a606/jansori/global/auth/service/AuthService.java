package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.domain.RefreshToken;
import com.a606.jansori.global.auth.dto.AuthReqDto;
import com.a606.jansori.global.auth.dto.AuthResDto;
import com.a606.jansori.global.auth.dto.TokenReqDto;
import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.auth.exception.AuthInvalidRefreshTokenException;
import com.a606.jansori.global.auth.exception.AuthMemberDuplicateException;
import com.a606.jansori.global.auth.exception.AuthMemberNotFoundException;
import com.a606.jansori.global.auth.exception.AuthUnauthorizedException;
import com.a606.jansori.global.auth.repository.RefreshTokenRepository;
import com.a606.jansori.global.auth.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public AuthResDto signup(AuthReqDto authReqDto) {

    if (memberRepository.existsByEmail(authReqDto.getEmail())) {

      throw new AuthMemberDuplicateException();

    }

    Member member = authReqDto.toMember(passwordEncoder);

    return AuthResDto.from(memberRepository.save(member));

  }

  @Transactional
  public TokenResDto login(AuthReqDto authReqDto) {

    UsernamePasswordAuthenticationToken authenticationToken = authReqDto.toAuthentication();

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    RefreshToken refreshToken = RefreshToken.builder()
        .email(authentication.getName())
        .value(tokenProvider.generateRefreshTokenDto())
        .build();

    TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
        refreshToken.getValue());

    refreshTokenRepository.save(refreshToken);

    return tokenResDto;

  }

  @Transactional
  public TokenResDto reissue(TokenReqDto tokenReqDto) {

    if (!tokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
      throw new AuthInvalidRefreshTokenException();
    }

    Authentication authentication = tokenProvider.getAuthentication(
        tokenReqDto.getAccessToken());

    // 로그아웃 된 사용자
    RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
        .orElseThrow(AuthMemberNotFoundException::new);

    // 토근 유저 정보 불일치
    if (!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())) {
      throw new AuthUnauthorizedException();
    }
    return tokenProvider.generateAccessTokenDto(authentication, refreshToken.getValue());
  }

}
