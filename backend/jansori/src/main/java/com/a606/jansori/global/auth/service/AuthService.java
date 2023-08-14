package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.DuplicatedEmailException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.global.auth.domain.RefreshToken;
import com.a606.jansori.global.auth.dto.AuthLoginReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupResDto;
import com.a606.jansori.global.auth.dto.TokenReqDto;
import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.auth.exception.InvalidTokenException;
import com.a606.jansori.global.auth.util.TokenProvider;
import com.a606.jansori.global.config.property.JwtConfigProperty;
import com.a606.jansori.global.exception.domain.UnauthorizedException;
import com.a606.jansori.infra.redis.util.BlackListUtil;
import com.a606.jansori.infra.redis.util.RefreshTokenUtil;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private final RefreshTokenUtil refreshTokenUtil;

  private final BlackListUtil redisBlackListUtil;

  private final JwtConfigProperty jwtConfigProperty;
  private final NotificationBoxRepository notificationBoxRepository;
  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;
  private final Clock clock;



  @Transactional
  public AuthSignupResDto signup(AuthSignupReqDto authSignupReqDto) {

    Member member = makeNewMember(authSignupReqDto);

    makeNotificationBoxOfNewMember(member);

    makeNotificationSettingOfNewMember(member);

    return AuthSignupResDto.from(member);

  }

  public Member makeNewMember(AuthSignupReqDto authSignupReqDto){

    if (memberRepository.existsByEmail(authSignupReqDto.getEmail())) {

      throw new DuplicatedEmailException();

    }

    Member member = authSignupReqDto.toMember(passwordEncoder);

    memberRepository.save(member);

    return member;
  }

  public void makeNotificationBoxOfNewMember(Member member){
    notificationBoxRepository.save(NotificationBox.builder()
        .member(member)
        .readAt(LocalDateTime.now(clock))
        .modifiedAt(LocalDateTime.now(clock))
        .build());
  }

  public void makeNotificationSettingOfNewMember(Member member){

    List<NotificationType> notificationTypes = notificationTypeRepository.findAll();

    List<NotificationSetting> settings = new ArrayList<>();

    for (NotificationType notificationType : notificationTypes){
      settings.add(NotificationSetting.builder()
          .member(member)
          .notificationType(notificationType)
          .build());
    }

    notificationSettingRepository.saveAll(settings);


  }

  @Transactional
  public TokenResDto login(AuthLoginReqDto authLoginReqDto) {

    UsernamePasswordAuthenticationToken authenticationToken = authLoginReqDto.toAuthentication();

    try {

      Member member = memberRepository.findMemberByEmail(authLoginReqDto.getEmail())
          .orElseThrow(MemberNotFoundException::new);

      Authentication authentication = authenticationManagerBuilder.getObject()
          .authenticate(authenticationToken);

      RefreshToken refreshToken = new RefreshToken(member.getEmail(),
          tokenProvider.generateRefreshTokenDto());

      refreshTokenUtil.save(refreshToken, jwtConfigProperty.getRefreshTokenExpireTime());

      TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
          refreshToken.getRefreshToken());

      boolean isUnreadNotificationLeft = notificationBoxRepository
          .findUnreadNotificationByMember(member).isPresent();

      tokenResDto.of(member, isUnreadNotificationLeft);

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
    RefreshToken refreshToken = refreshTokenUtil.findByEmail(authentication.getName())
        .orElseThrow(InvalidTokenException::new);

    // 토근 유저 정보 불일치
    if (!refreshToken.getRefreshToken().equals(tokenReqDto.getRefreshToken())) {
      throw new UnauthorizedException();
    }

    TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
        refreshToken.getRefreshToken());

    Member member = memberRepository.findMemberByEmail(authentication.getName())
        .orElseThrow(MemberNotFoundException::new);

    tokenResDto.from(member);

    return tokenResDto;
  }

  @Transactional
  public void logout(TokenReqDto tokenReqDto) {

    if (!tokenProvider.validateToken(tokenReqDto.getAccessToken())) {
      throw new InvalidTokenException();
    }

    Authentication authentication = tokenProvider.getAuthentication(tokenReqDto.getAccessToken());

    refreshTokenUtil.deleteByEmail(authentication.getName());

    redisBlackListUtil.setBlackList(tokenReqDto.getAccessToken(), "accessToken", tokenProvider.getExpiration(
        tokenReqDto.getAccessToken()));
  }
}
