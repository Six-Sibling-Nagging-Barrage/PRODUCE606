package com.a606.jansori.domain.tag.controller;

import com.a606.jansori.domain.tag.dto.GetAutoCompleteTagsResDto;
import com.a606.jansori.domain.tag.dto.GetFollowingTagResDto;
import com.a606.jansori.domain.tag.dto.GetTagSearchReqDto;
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
  public EnvelopeResponse followTagByTagWithMember(Long memberId, @PathVariable Long tagId) {
    tagService.followTagByTagWithMember(memberId, tagId);
    return EnvelopeResponse.builder().build();
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
      GetTagSearchReqDto getTagSearchReqDto) {
    return EnvelopeResponse.<GetAutoCompleteTagsResDto>builder()
        .data(tagService.getTagsBySearch(getTagSearchReqDto.getKeyword()))
        .build();
  }
}
