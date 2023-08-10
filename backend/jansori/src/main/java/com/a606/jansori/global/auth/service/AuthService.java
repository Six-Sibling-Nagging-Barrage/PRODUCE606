package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.DuplicatedEmailException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.domain.RefreshToken;
import com.a606.jansori.global.auth.dto.AuthLoginReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupResDto;
import com.a606.jansori.global.auth.dto.TokenReqDto;
import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.auth.exception.InvalidTokenException;
import com.a606.jansori.global.auth.repository.RefreshTokenRepository;
import com.a606.jansori.global.auth.util.TokenProvider;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
import com.a606.jansori.infra.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

  private final RedisUtil redisUtil;

  @Transactional
  public AuthSignupResDto signup(AuthSignupReqDto authSignupReqDto) {

    if (memberRepository.existsByEmail(authSignupReqDto.getEmail())) {

      throw new DuplicatedEmailException();

    }

    Member member = authSignupReqDto.toMember(passwordEncoder);

    memberRepository.save(member);

    return AuthSignupResDto.from(member);

  }

  @Transactional
  public TokenResDto login(AuthLoginReqDto authLoginReqDto) {

    UsernamePasswordAuthenticationToken authenticationToken = authLoginReqDto.toAuthentication();

    try {

      Member member = memberRepository.findMemberByEmail(authLoginReqDto.getEmail())
          .orElseThrow(MemberNotFoundException::new);

      Authentication authentication = authenticationManagerBuilder.getObject()
          .authenticate(authenticationToken);

      RefreshToken refreshToken = refreshTokenRepository.findByEmail(authLoginReqDto.getEmail())
          .orElse(RefreshToken.builder().email(authLoginReqDto.getEmail()).build());

      refreshToken.setValue(tokenProvider.generateRefreshTokenDto());

      refreshTokenRepository.save(refreshToken);

      TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
          refreshToken.getValue());

      tokenResDto.update(member);

      return tokenResDto;

    } catch (AuthenticationException e) {
      throw new UnauthorizedException();
    }
  }

  @Transactional
  public TokenResDto reissue(TokenReqDto tokenReqDto) {

    if (!tokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
      throw new InvalidTokenException();
    }

    Authentication authentication = tokenProvider.getAuthentication(
        tokenReqDto.getAccessToken());

    // 로그아웃 된 사용자
    RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
        .orElseThrow(InvalidTokenException::new);

    // 토근 유저 정보 불일치
    if (!refreshToken.getValue().equals(tokenReqDto.getRefreshToken())) {
      throw new UnauthorizedException();
    }

    TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
        refreshToken.getValue());

    Member member = memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new);

    tokenResDto.update(member);

    return tokenResDto;
  }

  @Transactional
  public void logout(TokenReqDto tokenReqDto) {

    try {
      if (!tokenProvider.validateToken(tokenReqDto.getAccessToken())) {
        throw new InvalidTokenException();
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new InvalidTokenException();
    }


    Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());

    refreshTokenRepository.deleteByEmail(authentication.getName());

    redisUtil.setBlackList(tokenReqDto.getAccessToken(), "accessToken", tokenProvider.getExpiration(
        tokenReqDto.getAccessToken()));
  }
}
