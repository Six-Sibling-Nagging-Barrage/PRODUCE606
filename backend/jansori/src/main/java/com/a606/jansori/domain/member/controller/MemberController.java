package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.*;
import com.a606.jansori.domain.member.service.MemberService;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

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

  @GetMapping("/my/profile")
  public EnvelopeResponse<GetMyProfileResDto> getMyProfile() {

    return EnvelopeResponse.<GetMyProfileResDto>builder()
        .data(memberService.getMyProfile())
        .build();
  }

  @PatchMapping("/update")
  public EnvelopeResponse<PatchMemberInfoResDto> updateMemberInfo(
      @RequestBody @Valid PatchMemberInfoReqDto patchMemberInfoReqDto) {

    return EnvelopeResponse.<PatchMemberInfoResDto>builder()
        .data(memberService.updateMemberInfo(patchMemberInfoReqDto))
        .build();

  }

}
