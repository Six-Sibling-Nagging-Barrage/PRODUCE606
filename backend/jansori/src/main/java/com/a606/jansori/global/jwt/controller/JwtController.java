package com.a606.jansori.global.jwt.controller;

import com.a606.jansori.global.jwt.dto.MemberReqDto;
import com.a606.jansori.global.jwt.dto.MemberResDto;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.global.jwt.dto.TokenDto;
import com.a606.jansori.global.jwt.dto.TokenRequestDto;
import com.a606.jansori.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController {

  private final JwtService jwtService;

  @PostMapping("/signup")
  public EnvelopeResponse<MemberResDto> signup(@RequestBody MemberReqDto memberReqDto) {

    return EnvelopeResponse.<MemberResDto>builder()
        .data(jwtService.signup(memberReqDto))
        .build();

  }

  @PostMapping("/login")
  public EnvelopeResponse<TokenDto> login(@RequestBody MemberReqDto memberReqDto) {

    return EnvelopeResponse.<TokenDto>builder()
        .data(jwtService.login(memberReqDto))
        .build();

  }

  @PostMapping("/reissue")
  public EnvelopeResponse<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {

    return EnvelopeResponse.<TokenDto>builder()
        .data(jwtService.reissue(tokenRequestDto))
        .build();

  }

}
