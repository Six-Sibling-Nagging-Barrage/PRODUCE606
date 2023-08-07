package com.a606.jansori.global.auth.controller;

import com.a606.jansori.global.auth.dto.AuthReqDto;
import com.a606.jansori.global.auth.dto.AuthResDto;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.global.auth.dto.TokenDto;
import com.a606.jansori.global.auth.dto.TokenRequestDto;
import com.a606.jansori.global.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final SecurityUtil securityUtil;

  @PostMapping("/signup")
  public EnvelopeResponse<AuthResDto> signup(@Valid @RequestBody AuthReqDto authReqDto) {
    return EnvelopeResponse.<AuthResDto>builder()
        .data(authService.signup(authReqDto))
        .build();
  }

  @PostMapping("/login")
  public EnvelopeResponse<TokenDto> login(@Valid @RequestBody AuthReqDto authReqDto) {

    return EnvelopeResponse.<TokenDto>builder()
        .data(authService.login(authReqDto))
        .build();

  }

  @PostMapping("/reissue")
  public EnvelopeResponse<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {

    return EnvelopeResponse.<TokenDto>builder()
        .data(authService.reissue(tokenRequestDto))
        .build();

  }

}
