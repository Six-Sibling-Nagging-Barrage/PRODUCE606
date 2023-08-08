package com.a606.jansori.global.auth.controller;

import com.a606.jansori.global.auth.dto.AuthLoginReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupResDto;
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
  public EnvelopeResponse<AuthSignupResDto> signup(
      @Valid @RequestBody AuthSignupReqDto authSignupReqDto) {

    return EnvelopeResponse.<AuthSignupResDto>builder()
        .data(authService.signup(authSignupReqDto))
        .build();

  }

  @PostMapping("/login")
  public EnvelopeResponse<TokenResDto> login(@Valid @RequestBody AuthLoginReqDto authLoginReqDto) {

    return EnvelopeResponse.<TokenResDto>builder()
        .data(authService.login(authLoginReqDto))
        .build();

  }

  @PostMapping("/reissue")
  public EnvelopeResponse<TokenResDto> reissue(@RequestBody TokenReqDto tokenReqDto) {

    return EnvelopeResponse.<TokenResDto>builder()
        .data(authService.reissue(tokenReqDto))
        .build();

  }

}
