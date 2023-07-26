package com.a606.jansori.domain.tag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
