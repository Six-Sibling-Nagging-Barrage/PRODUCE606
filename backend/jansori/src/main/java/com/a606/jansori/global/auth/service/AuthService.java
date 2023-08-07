package com.a606.jansori.global.auth.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.global.auth.domain.RefreshToken;
import com.a606.jansori.global.auth.dto.*;
import com.a606.jansori.global.auth.exception.*;
import com.a606.jansori.global.auth.repository.RefreshTokenRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.global.auth.util.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final TagFollowRepository tagFollowRepository;
  private final TagRepository tagRepository;

  @Transactional
  public AuthSignupResDto signup(AuthSignupReqDto authSignupReqDto, String imageName) {

    if (memberRepository.existsByEmail(authSignupReqDto.getEmail())) {

      throw new AuthMemberDuplicateException();

    }

    Member member = authSignupReqDto.toMember(passwordEncoder, imageName);

    memberRepository.save(member);

    List<Long> tags = authSignupReqDto.getTags();

    if (tags != null) {
      for (Long tagId : tags) {
        Tag tag = tagRepository.findTagById(tagId).orElseThrow(TagNotFoundException::new);

        followTags(member, tag);
      }
    }

    return AuthSignupResDto.from(memberRepository.save(member));

  }

  private void followTags(Member member, Tag tag) {

    if (tagFollowRepository.findTagFollowByTagAndMember(tag, member).isEmpty()) {

      tagFollowRepository.save(TagFollow.builder()
              .member(member)
              .tag(tag)
              .build());

    }
  }

  @Transactional
  public TokenResDto login(AuthLoginReqDto authLoginReqDto) {

    UsernamePasswordAuthenticationToken authenticationToken = authLoginReqDto.toAuthentication();

    try {
      Authentication authentication = authenticationManagerBuilder.getObject()
              .authenticate(authenticationToken);

      RefreshToken refreshToken = RefreshToken.builder()
              .email(authentication.getName())
              .value(tokenProvider.generateRefreshTokenDto())
              .build();

      TokenResDto tokenResDto = tokenProvider.generateAccessTokenDto(authentication,
              refreshToken.getValue());

      Member member = memberRepository.findMemberByEmail(authLoginReqDto.getEmail())
              .orElseThrow(AuthMemberNotFoundException::new);

      Long memberId = member.getId();

      tokenResDto.update(memberId);

      refreshTokenRepository.save(refreshToken);

      return tokenResDto;

    } catch (AuthenticationException e) {
      throw new AuthInvalidPasswordException();
    }
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
