package com.a606.jansori.global.jwt.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.global.jwt.dto.MemberReqDto;
import com.a606.jansori.global.jwt.dto.MemberResDto;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.jwt.entity.RefreshToken;
import com.a606.jansori.global.jwt.dto.TokenDto;
import com.a606.jansori.global.jwt.dto.TokenRequestDto;
import com.a606.jansori.global.jwt.repository.RefreshTokenRepository;
import com.a606.jansori.global.jwt.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  @Transactional
  public MemberResDto signup(MemberReqDto memberReqDto) {

    if (memberRepository.existsByEmail(memberReqDto.getEmail())) {

      throw new RuntimeException("이미 가입되어 있는 유저입니다");

    }

    Member member = memberReqDto.toMember(passwordEncoder);

    return MemberResDto.of(memberRepository.save(member));

  }

  @Transactional
  public TokenDto login(MemberReqDto memberReqDto) {

    UsernamePasswordAuthenticationToken authenticationToken = memberReqDto.toAuthentication();

    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

    RefreshToken refreshToken = RefreshToken.builder()
        .key(authentication.getName())
        .value(tokenDto.getRefreshToken())
        .build();

    refreshTokenRepository.save(refreshToken);

    return tokenDto;

  }

  @Transactional
  public TokenDto reissue(TokenRequestDto tokenRequestDto) {

    if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
      throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
    }

    Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

    RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
        .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

    if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
      throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
    }

    TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

    RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
    refreshTokenRepository.save(newRefreshToken);

    return tokenDto;
  }

}
