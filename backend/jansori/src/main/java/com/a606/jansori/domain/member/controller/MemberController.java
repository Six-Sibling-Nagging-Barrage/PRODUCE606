package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.dto.*;
import com.a606.jansori.domain.member.service.MemberService;
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
        .data(memberService.checkNicknameIsAvailable(getDuplicateNicknameReqDto))
        .build();
  }

  @GetMapping("/{memberId}/profile")
  public EnvelopeResponse<GetUserProfileResDto> getUserProfile(@PathVariable Long memberId) {
    return EnvelopeResponse.<GetUserProfileResDto>builder()
        .data(memberService.getUserProfile(memberId))
        .build();
  }

  @GetMapping("/my/profile")
  public EnvelopeResponse<GetMyProfileResDto> getMyProfile(@RequestParam Long memberId) {
    return EnvelopeResponse.<GetMyProfileResDto>builder()
        .data(memberService.getMyProfile(memberId))
        .build();
  }

  @PostMapping("/register")
  public EnvelopeResponse<PostMemberInfoResDto> postMemberInfo(
          Long memberId, @RequestBody @Valid PostMemberInfoReqDto postMemberInfoReqDto){
    return EnvelopeResponse.<PostMemberInfoResDto>builder()
            .data(memberService.UpdateMemberInfo(memberId, postMemberInfoReqDto))
            .build();

  }

}
