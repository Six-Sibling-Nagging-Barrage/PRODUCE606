package com.a606.jansori.domain.tag.controller;

import com.a606.jansori.domain.tag.service.TagService;
import com.a606.jansori.global.common.EnvelopeResponse;
import lombok.RequiredArgsConstructor;
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
}
