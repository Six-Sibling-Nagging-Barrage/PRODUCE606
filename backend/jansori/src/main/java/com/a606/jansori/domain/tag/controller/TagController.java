package com.a606.jansori.domain.tag.controller;

import com.a606.jansori.domain.tag.dto.GetAutoCompleteTagsResDto;
import com.a606.jansori.domain.tag.dto.GetFollowingTagResDto;
import com.a606.jansori.domain.tag.dto.GetTagReqDto;
import com.a606.jansori.domain.tag.dto.PostTagFollowResDto;
import com.a606.jansori.domain.tag.service.TagService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

  private final TagService tagService;

  @PostMapping("/{tagId}/follow")
  public EnvelopeResponse<PostTagFollowResDto> followTagByTagWithMember(@PathVariable Long tagId) {
    return EnvelopeResponse.<PostTagFollowResDto>builder()
        .data(tagService.followTagByTagWithMember(tagId))
        .build();
  }

  @GetMapping("/{memberId}/list")
  public EnvelopeResponse<GetFollowingTagResDto> getFollowingTagByMemberId(
      @PathVariable Long memberId) {
    return EnvelopeResponse.<GetFollowingTagResDto>builder()
        .data(tagService.getFollowingTagByMemberId(memberId))
        .build();
  }

  @GetMapping("/auto-complete")
  public EnvelopeResponse<GetAutoCompleteTagsResDto> getTagsBySearch(
      GetTagReqDto getTagReqDto) {
    return EnvelopeResponse.<GetAutoCompleteTagsResDto>builder()
        .data(tagService.getTagsBySearch(getTagReqDto.getKeyword()))
        .build();
  }
}
