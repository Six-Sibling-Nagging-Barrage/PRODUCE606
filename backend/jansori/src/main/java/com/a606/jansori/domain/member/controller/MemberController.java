package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.*;
import com.a606.jansori.domain.member.service.MemberService;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.storage.Service.AwsS3Service;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;
  private final AwsS3Service awsS3Service;
  private final String UPLOAD_DIR = "upload";

  // 닉네임 중복 검사
  @GetMapping("/nickname")
  public EnvelopeResponse<GetDuplicateNicknameResDto> checkNicknameAvailable(
      @RequestBody GetDuplicateNicknameReqDto getDuplicateNicknameReqDto) {

    return EnvelopeResponse.<GetDuplicateNicknameResDto>builder()
        .data(memberService.checkNicknameIsDuplicated(getDuplicateNicknameReqDto))
        .build();
  }

  @GetMapping("/{memberId}/profile")
  public EnvelopeResponse<GetMemberProfileResDto> getMemberProfile(@PathVariable Long memberId) {

    return EnvelopeResponse.<GetMemberProfileResDto>builder()
        .data(memberService.getMemberProfile(memberId))
        .build();
  }

  @PostMapping("/update")
  public EnvelopeResponse<PatchMemberInfoResDto> updateMemberInfo(
      @RequestPart(value = "imageFile", required = false) MultipartFile multipartFile,
      @RequestPart(value = "memberInfo") @Valid PatchMemberInfoReqDto patchMemberInfoReqDto)
      throws IOException {

    String imageName = null;

    if(multipartFile != null) {
      PostFileUploadResDto postFileUploadResDto =
              awsS3Service.uploadFile(
                      PostFileUploadReqDto
                              .builder()
                              .multipartFile(multipartFile)
                              .build(), UPLOAD_DIR);
      imageName = postFileUploadResDto.getImageName();
    }

    return EnvelopeResponse.<PatchMemberInfoResDto>builder()
            .data(memberService
                .updateMemberInfo(patchMemberInfoReqDto, imageName))
            .build();

  }

}
