package com.a606.jansori.domain.member.controller;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.GetUserProfileResDto;
import com.a606.jansori.domain.member.dto.PostNicknameReqDto;
import com.a606.jansori.domain.member.dto.PostNicknameResDto;
import com.a606.jansori.domain.member.service.MemberService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private MemberService memberService;

    @PostMapping("/nickname-check")
    public EnvelopeResponse<PostNicknameResDto> checkNicknameAvailable(@Valid @RequestBody PostNicknameReqDto postNicknameReqDto){
        return EnvelopeResponse.<PostNicknameResDto>builder()
                .data(memberService.checkNicknameAvailable(postNicknameReqDto))
                .build();
    }

    @GetMapping("/{memberId}/profile")
    public EnvelopeResponse<GetUserProfileResDto> getUserProfile(@PathVariable Long memberId) {

        Member member;
        return null;
    }
}
