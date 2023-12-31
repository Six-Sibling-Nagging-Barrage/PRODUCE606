package com.a606.jansori.domain.nag.controller;

import com.a606.jansori.domain.nag.dto.GetMemberNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfMainPageResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfNagBoxResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfOtherResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfResDto;
import com.a606.jansori.domain.nag.dto.NagDto;
import com.a606.jansori.domain.nag.dto.PostNagLikeResDto;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.service.NagService;
import com.a606.jansori.global.common.EnvelopeResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nags")
public class NagController {

  private final NagService nagService;

  @GetMapping("/my-list")
  public EnvelopeResponse<GetNagsOfResDto> getAllNagsByMine(
      @Valid GetNagsOfReqDto getNagsOfReqDto) {
    return EnvelopeResponse.<GetNagsOfResDto>builder()
        .data(nagService.getAllNagsByMine(getNagsOfReqDto))
        .build();
  }

  @GetMapping("/main-page")
  public EnvelopeResponse<GetNagOfMainPageResDto> getRandomNagsOfMainPage() {
    return EnvelopeResponse.<GetNagOfMainPageResDto>builder()
        .data(nagService.getRandomNagsOfMainPage())
        .build();
  }

  @GetMapping("/nag-rank")
  public EnvelopeResponse<GetNagsOfNagBoxResDto> getNagsOfNagBox() {
    return EnvelopeResponse.<GetNagsOfNagBoxResDto>builder()
        .data(nagService.getNagsOfNagBox())
        .build();
  }

  @GetMapping("/nag-box/statistics")
  public EnvelopeResponse<GetNagBoxStatisticsResDto> getNagBoxStatistics() {
    return EnvelopeResponse.<GetNagBoxStatisticsResDto>builder()
        .data(nagService.getNagBoxStatisticsResDto())
        .build();
  }

  @GetMapping("/members")
  public EnvelopeResponse<GetNagsOfOtherResDto> getNagsOfProfilePageByMemberId(
      @Valid GetMemberNagsOfReqDto getMemberNagsOfReqDto) {
    return EnvelopeResponse.<GetNagsOfOtherResDto>builder()
        .data(nagService.getMemberNagsByMemberId(getMemberNagsOfReqDto))
        .build();
  }

  @PostMapping
  public EnvelopeResponse<PostNagResDto> postNaggingByMember(
      @Valid @RequestBody PostNagReqDto postNagReqDto) {
    return EnvelopeResponse.<PostNagResDto>builder()
        .data(nagService.createNag(postNagReqDto))
        .build();
  }

  @PostMapping("/{nagId}/like")
  public EnvelopeResponse<PostNagLikeResDto> toggleNagLike(@PathVariable Long nagId) {
    return EnvelopeResponse.<PostNagLikeResDto>builder()
        .data(nagService.toggleNagLike(nagId))
        .build();
  }

  @PutMapping("/{nagId}/unlock")
  public EnvelopeResponse<NagDto> unlockNagPreviewByMemberTicket(@PathVariable Long nagId) {
    return EnvelopeResponse.<NagDto>builder()
        .data(nagService.unlockNagPreviewByMemberTicket(nagId))
        .build();
  }
}
