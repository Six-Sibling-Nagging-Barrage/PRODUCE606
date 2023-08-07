package com.a606.jansori.domain.nag.controller;

import com.a606.jansori.domain.nag.dto.GetNagsOfProfilePageReqDto;
import com.a606.jansori.domain.nag.task.NagBoxStatisticsScheduler;
import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfMainPageResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfProfilePageResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfNagBoxResDto;
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
  private final NagBoxStatisticsScheduler nagBoxStatisticsScheduler;

  @GetMapping("/my-list")
  public EnvelopeResponse<GetNagOfProfilePageResDto> getAllNagsByMine() {
    return EnvelopeResponse.<GetNagOfProfilePageResDto>builder()
        .data(nagService.getAllNagsByMine())
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
  public EnvelopeResponse<GetNagBoxStatisticsResDto> getNagBoxStatisticsByScheduling() {
    return EnvelopeResponse.<GetNagBoxStatisticsResDto>builder()
        .data(nagBoxStatisticsScheduler.getNagBoxStatisticsResDto())
        .build();
  }

  @GetMapping("/nags/members")
  public EnvelopeResponse getNagsOfProfilePageByMemberId(
      @Valid GetNagsOfProfilePageReqDto getNagsOfProfilePageReqDto) {
      return EnvelopeResponse.builder()
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
