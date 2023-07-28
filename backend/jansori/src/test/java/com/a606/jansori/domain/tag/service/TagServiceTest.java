package com.a606.jansori.domain.tag.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

  @Mock
  private TagRepository tagRepository;

  @Mock
  private TagFollowRepository tagFollowRepository;

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private TagService tagService;

  private Tag tag;
  private Member member;
  private TagFollow tagFollow;

  @BeforeEach
  void setUp() {
    tag = Tag.builder()
        .id(1L)
        .build();

    member = Member.builder()
        .id(1L)
        .build();

    tagFollow = TagFollow.builder()
        .tag(tag)
        .member(member)
        .build();
  }

  @DisplayName("tag를 찾을 수 없으면 태그 팔로우를 실패한다.")
  @Test
  void Given_Invalid_TagId_When_Following_Tag_Then_Fail() {
    //given
    given(tagRepository.findById(tag.getId())).willReturn(Optional.empty());

    //then
    assertThrows(TagNotFoundException.class,
        () -> tagService.followTagByTagWithMember(member.getId(), tag.getId()));

    //verify
    verify(tagRepository, times(1)).findById(tag.getId());
  }

  @DisplayName("tag가 존재한다면 태그 팔로우에 성공한다.")
  @Test
  void Given_Valid_Tag_With_Member_When_Following_Tag_Then_Success() {
    //given
    given(tagRepository.findById(tag.getId())).willReturn(Optional.of(tag));
    given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    given(tagFollowRepository.findTagFollowByTagAndMember(tag, member)).willReturn(Optional.empty());

    //when
    when(tagFollowRepository.save(any(TagFollow.class))).thenReturn(null);

    //then
    tagService.followTagByTagWithMember(member.getId(), tag.getId());

    //verify
    verify(tagRepository, times(1)).findById(tag.getId());
    verify(memberRepository, times(1)).findById(member.getId());
    verify(tagFollowRepository, times(1)).save(any(TagFollow.class));
    verify(tagFollowRepository, times(1)).findTagFollowByTagAndMember(tag, member);
  }

  @DisplayName("tag가 존재하고 이미 팔로우한 태그라면 팔로우에 취소한다.")
  @Test
  void Given_Valid_Tag_With_Member_When_Exist_Following_Tag_Then_Success() {
    //given
    given(tagRepository.findById(tag.getId())).willReturn(Optional.of(tag));
    given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
    given(tagFollowRepository.findTagFollowByTagAndMember(tag, member)).willReturn(Optional.of(tagFollow));

    //then
    tagService.followTagByTagWithMember(member.getId(), tag.getId());

    //verify
    verify(tagRepository, times(1)).findById(tag.getId());
    verify(memberRepository, times(1)).findById(member.getId());
    verify(tagFollowRepository, times(1)).findTagFollowByTagAndMember(tag, member);
  }
}