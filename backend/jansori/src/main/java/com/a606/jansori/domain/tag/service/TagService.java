package com.a606.jansori.domain.tag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.dto.GetAutoCompleteTagsResDto;
import com.a606.jansori.domain.tag.dto.GetFollowingTagResDto;
import com.a606.jansori.domain.tag.dto.GetTagReqDto;
import com.a606.jansori.domain.tag.exception.TagDuplicateException;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.tag.dto.TagDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TagService {

  private final TagRepository tagRepository;
  private final TagFollowRepository tagFollowRepository;
  private final MemberRepository memberRepository;
  private final int PAGE_START = 0;
  private final int AUTO_COMPLETE_SIZE = 10;

  @Transactional
  public void followTagByTagWithMember(Long memberId, Long tagId) {

    Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    Optional<TagFollow> tagFollow = tagFollowRepository.findTagFollowByTagAndMember(tag, member);

    tagFollow.ifPresentOrElse(tagFollowRepository::delete,
        () -> tagFollowRepository.save(TagFollow.builder().tag(tag).member(member).build()));
  }

  @Transactional
  public TagDto createTagByKeyword(Long memberId, String name) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    boolean isExist = tagRepository.existsTagByName(name);

    if(isExist) {
      throw new TagDuplicateException();
    }

    Tag tag = tagRepository.save(Tag.fromKeyword(name));
    tagFollowRepository.save(TagFollow.fromTagAndMember(tag, member));

    return TagDto.from(tag);
  }

  @Transactional(readOnly = true)
  public GetFollowingTagResDto getFollowingTagByMemberId(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

    List<TagFollow> tagFollows = tagFollowRepository.findTagFollowsByMember(member);
    return GetFollowingTagResDto.from(
        tagFollows.stream().map(TagDto::from).collect(Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public GetAutoCompleteTagsResDto getTagsBySearch(String keyword) {
    List<Tag> tags = tagRepository.findTagsByNameContainingIgnoreCaseOrderByFollowCountDesc(
        keyword, PageRequest.of(PAGE_START, AUTO_COMPLETE_SIZE));

    return GetAutoCompleteTagsResDto.fromTagsBySearch(tags
        .stream()
        .map(TagDto::from)
        .collect(Collectors.toList()));
  }
}
