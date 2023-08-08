package com.a606.jansori.global.auth.controller;

import com.a606.jansori.global.auth.dto.AuthLoginReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupReqDto;
import com.a606.jansori.global.auth.dto.AuthSignupResDto;
import com.a606.jansori.global.auth.dto.TokenReqDto;
import com.a606.jansori.global.auth.dto.TokenResDto;
import com.a606.jansori.global.auth.service.AuthService;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.storage.Service.AwsS3Service;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AwsS3Service awsS3Service;
  private final String UPLOAD_DIR = "upload";

  @PostMapping("/signup")
  public EnvelopeResponse<AuthSignupResDto> signup(
      @RequestPart(value = "imageFile", required = false) MultipartFile multipartFile,
      @Valid @RequestPart(value = "memberInfo") AuthSignupReqDto authSignupReqDto)
      throws IOException {

    String imageName = null;

    if (multipartFile != null) {
      PostFileUploadResDto postFileUploadResDto =
          awsS3Service.uploadFile(
              PostFileUploadReqDto
                  .builder()
                  .multipartFile(multipartFile)
                  .build(), UPLOAD_DIR);
      imageName = postFileUploadResDto.getImageName();
    }

    return EnvelopeResponse.<AuthSignupResDto>builder()
        .data(authService.signup(authSignupReqDto, imageName))
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
