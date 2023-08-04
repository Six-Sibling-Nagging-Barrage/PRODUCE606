package com.a606.jansori.domain.tag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.dto.GetAutoCompleteTagsResDto;
import com.a606.jansori.domain.tag.dto.GetFollowingTagResDto;
import com.a606.jansori.domain.tag.dto.PostTagFollowResDto;
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
  public PostTagFollowResDto followTagByTagWithMember(Long memberId, Long tagId) {

    Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    Member member = memberRepository.findById(51L).orElseThrow(MemberNotFoundException::new);

    Optional<TagFollow> tagFollow = tagFollowRepository.findTagFollowByTagAndMember(tag, member);
    PostTagFollowResDto postTagFollowResDto = new PostTagFollowResDto();


    tagFollow.ifPresentOrElse(follow -> cancelTagFollow(follow, postTagFollowResDto),
        () -> followingTag(tag, member, postTagFollowResDto));

    return postTagFollowResDto;
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


  private void cancelTagFollow(TagFollow tagFollow, PostTagFollowResDto postTagFollowResDto) {
    tagFollowRepository.delete(tagFollow);
    tagFollow.getTag().decreaseFollowCount();
    postTagFollowResDto.cancelFollowingTag();
  }

  private void followingTag(Tag tag, Member member, PostTagFollowResDto postTagFollowResDto) {
    tagFollowRepository.save(TagFollow.fromTagAndMember(tag, member));
    tag.increaseFollowCount();
    postTagFollowResDto.followingTag();
  }
}
