package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.dto.GetMemberProfileResDto;
import com.a606.jansori.domain.member.dto.PostMemberInfoReqDto;
import com.a606.jansori.domain.member.dto.PostMemberInfoResDto;
import com.a606.jansori.domain.member.service.MemberService;
import com.a606.jansori.global.common.EnvelopeResponse;
import com.a606.jansori.infra.storage.Service.AwsS3Service;
import com.a606.jansori.infra.storage.dto.PostFileUploadReqDto;
import com.a606.jansori.infra.storage.dto.PostFileUploadResDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
      @Valid GetDuplicateNicknameReqDto getDuplicateNicknameReqDto) {

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

  @PostMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE})
  public EnvelopeResponse<PostMemberInfoResDto> updateMemberInfo(
      @RequestPart(value = "imageFile", required = false) MultipartFile multipartFile,
      @RequestPart(value = "memberInfo") @Valid PostMemberInfoReqDto postMemberInfoReqDto) {

    String imageName = null;

    if (multipartFile != null) {

      PostFileUploadResDto postFileUploadResDto =
          null;

      postFileUploadResDto = awsS3Service.uploadImageFileToS3(
          PostFileUploadReqDto
              .builder()
              .multipartFile(multipartFile)
              .build());

      imageName = postFileUploadResDto.getImageUrl();

    }

    return EnvelopeResponse.<PostMemberInfoResDto>builder()
        .data(memberService.updateMemberInfo(postMemberInfoReqDto, imageName))
        .build();

  }

}
