package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.auth.dto.AuthReqDto;
import com.a606.jansori.global.auth.dto.AuthResDto;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.entity.PrincipalDetails;
import com.a606.jansori.global.auth.entity.RefreshToken;
import com.a606.jansori.global.auth.dto.TokenDto;
import com.a606.jansori.global.auth.dto.TokenRequestDto;
import com.a606.jansori.global.auth.exception.AuthInvalidRefreshTokenException;
import com.a606.jansori.global.auth.exception.AuthMemberDuplicateException;
import com.a606.jansori.global.auth.exception.AuthMemberNotFoundException;
import com.a606.jansori.global.auth.exception.AuthUnauthorizedException;
import com.a606.jansori.global.auth.repository.RefreshTokenRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
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
  private final SecurityUtil securityUtil;

  @Transactional
  public AuthResDto signup(AuthReqDto authReqDto) {

    if (memberRepository.existsByEmail(authReqDto.getEmail())) {

      throw new AuthMemberDuplicateException();

    }

    Member member = authReqDto.toMember(passwordEncoder);

    return AuthResDto.of(memberRepository.save(member));

  }

  @Transactional
  public TokenDto login(AuthReqDto authReqDto) {

    // 리프레시 토큰이 만료되었으면 재발급해주는 로직 추가
    UsernamePasswordAuthenticationToken authenticationToken = authReqDto.toAuthentication();

    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);

    log.info(authentication.getName());

    // 리프레시 토큰을 만든다.
//    RefreshToken refreshToken = RefreshToken.builder()
//        .key(authentication.getName())
//        .value(tokenProvider.generateRefreshTokenDto())
//        .build();

    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenProvider.generateRefreshTokenDto())
        .build();

    // Authentication과 리프레시 토큰을 같이 보낸다.
    // TokenDto를 만든다.
    TokenDto tokenDto = tokenProvider.generateAccessTokenDto(authentication,
        refreshToken.getValue());

    refreshTokenRepository.save(refreshToken);

    return tokenDto;

  }

  @Transactional
  public TokenDto reissue(TokenRequestDto tokenRequestDto) {

    if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
      throw new AuthInvalidRefreshTokenException();
    }

    Authentication authentication = tokenProvider.getAuthentication(
        tokenRequestDto.getAccessToken());

    // 로그아웃 된 사용자
    RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
        .orElseThrow(() -> new AuthMemberNotFoundException());

    // 토근 유저 정보 불일치
    if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
      throw new AuthUnauthorizedException();
    }

    TokenDto tokenDto = tokenProvider.generateAccessTokenDto(
        authentication, refreshToken.getValue());
    // generateToken -> 어떤 토큰들을 만드는지 불명확해
    // 액세스 토큰과 리프레시 토큰 모두 발급한다는 네이밍이 필요할듯

    return tokenDto;
  }

}
