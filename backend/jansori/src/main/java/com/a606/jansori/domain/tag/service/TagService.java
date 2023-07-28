package com.a606.jansori.domain.tag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.dto.GetFollowingTagResDto;
import com.a606.jansori.domain.tag.dto.TagElement;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TagFollowRepository tagFollowRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public void followTagByTagWithMember(Long memberId, Long tagId) {

    Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    Optional<TagFollow> tagFollow = tagFollowRepository.findTagFollowByTagAndMember(tag, member);

    tagFollow.ifPresentOrElse(tagFollowRepository::delete,
        () -> tagFollowRepository.save(TagFollow.builder().tag(tag).member(member).build()));
  }

  @Transactional(readOnly = true)
  public GetFollowingTagResDto getFollowingTagByMemberId(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    List<TagFollow> tagFollows = tagFollowRepository.findTagFollowsByMember(member);
    return GetFollowingTagResDto.from(
        tagFollows.stream().map(TagElement::new).collect(Collectors.toList()));
  }
}
