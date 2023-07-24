package com.a606.jansori.domain.nag.controller;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.global.common.EnvelopeResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/nags")
public class NagController {

    @PostMapping
    public EnvelopeResponse postNaggingByMember(Long memberId, @Valid @RequestBody PostNagReqDto postNagReqDto) {
        return EnvelopeResponse.builder()
                .build();
    }
}
