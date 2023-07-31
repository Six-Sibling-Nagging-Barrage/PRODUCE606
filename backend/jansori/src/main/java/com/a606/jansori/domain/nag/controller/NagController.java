package com.a606.jansori.domain.nag.controller;

import com.a606.jansori.domain.nag.dto.GetNagResDto;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.service.NagService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nags")
public class NagController {

    private final NagService nagService;

    @GetMapping("/my-list")
    public EnvelopeResponse<GetNagResDto> getAllNagsByMember(Long memberId) {
        return EnvelopeResponse.<GetNagResDto>builder()
            .data(nagService.getAllNagsByMember(memberId))
            .build();
    }

    @PostMapping
    public EnvelopeResponse<PostNagResDto> postNaggingByMember(
            Long memberId,
            @Valid @RequestBody PostNagReqDto postNagReqDto) {

        return EnvelopeResponse.<PostNagResDto>builder()
                .data(nagService.createNag(1L, postNagReqDto))
                .build();
    }

    @PostMapping("/{nagId}/like")
    public EnvelopeResponse toggleNagLike(
            Long memberId,
            @PathVariable Long nagId) {

        nagService.toggleNagLike(1L, nagId);
        return EnvelopeResponse.builder()
                .build();
    }
}
