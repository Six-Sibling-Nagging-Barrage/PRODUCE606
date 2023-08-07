package com.a606.jansori.global.auth.controller;

import com.a606.jansori.global.auth.dto.AuthReqDto;
import com.a606.jansori.global.auth.dto.AuthResDto;
import com.a606.jansori.global.auth.dto.TokenReqDto;
import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.auth.service.AuthService;
import com.a606.jansori.global.common.EnvelopeResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public EnvelopeResponse<AuthResDto> signup(@Valid @RequestBody AuthReqDto authReqDto) {
    return EnvelopeResponse.<AuthResDto>builder()
        .data(authService.signup(authReqDto))
        .build();
  }

  @PostMapping("/login")
  public EnvelopeResponse<TokenResDto> login(@Valid @RequestBody AuthReqDto authReqDto) {

    return EnvelopeResponse.<TokenResDto>builder()
        .data(authService.login(authReqDto))
        .build();

  }

  @PostMapping("/reissue")
  public EnvelopeResponse<TokenResDto> reissue(@RequestBody TokenReqDto tokenReqDto) {

    return EnvelopeResponse.<TokenResDto>builder()
        .data(authService.reissue(tokenReqDto))
        .build();

  }

}
