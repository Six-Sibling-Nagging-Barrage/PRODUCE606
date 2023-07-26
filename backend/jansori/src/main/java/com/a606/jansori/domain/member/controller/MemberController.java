package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.GetUserProfileResDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.service.MemberService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 닉네임 중복 검사
    @GetMapping("/nickname")
    public EnvelopeResponse<GetDuplicateNicknameResDto> checkNicknameAvailable(@RequestBody GetDuplicateNicknameReqDto getDuplicateNicknameReqDto){
        return EnvelopeResponse.<GetDuplicateNicknameResDto>builder()
                .data(memberService.checkNicknameIsAvailable(getDuplicateNicknameReqDto))
                .build();
    }

    @GetMapping("/{memberId}/profile")
    public EnvelopeResponse<GetUserProfileResDto> getUserProfile(@PathVariable Long memberId) {

        Member member;
        return null;
    }
}
