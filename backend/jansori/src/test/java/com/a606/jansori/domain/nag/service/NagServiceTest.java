package com.a606.jansori.domain.nag.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagLike;
import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfMainPageResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfProfilePageResDto;
import com.a606.jansori.domain.nag.dto.NagDto;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.exception.NagNotFoundException;
import com.a606.jansori.domain.nag.repository.NagLikeRepository;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.nag.repository.NagUnlockRepository;
import com.a606.jansori.domain.nag.util.PreviewUtil;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

  @Mock
  private NagRepository nagRepository;
  @Mock
  private TagRepository tagRepository;
  @Mock
  private MemberRepository memberRepository;
  @Mock
  private NagLikeRepository nagLikeRepository;
  @Mock
  private TodoRepository todoRepository;
  @Mock
  private PreviewUtil previewUtil;
  @Mock
  private SecurityUtil securityUtil;
  @Mock
  private NagUnlockRepository nagUnlockRepository;
  @Mock
  private NagRandomGenerator nagRandomGenerator;
  @InjectMocks
  private NagService nagService;
  private PostNagReqDto postNagReqDto;
  private Member member;
  private Tag tag;
  private Nag nag;
  private NagLike nagLike;

  @BeforeEach
  public void createPostNag() {
    member = Member.builder()
        .id(1L)
        .ticket(1L)
        .build();
    tag = Tag.builder()
        .id(1L)
        .name("운동")
        .followCount(1)
        .build();
    nag = Nag.builder()
        .id(1L)
        .tag(tag)
        .content("운동하자")
        .build();
    nagLike = NagLike.builder()
        .nag(nag)
        .member(member)
        .build();
  }

  @DisplayName("잔소리 해시태그가 존재하지 않는 ID 라면 잔소리 생성에 실패한다.")
  @Test
  void Given_NotExistHashTag_When_SaveNag_Then_Fail() {
    //given
    postNagReqDto = new PostNagReqDto("공부 안할래?", tag.getId());
    given(tagRepository.findById(tag.getId())).willReturn(Optional.empty());

    //when with then
    assertThrows(TagNotFoundException.class,
        () -> nagService.createNag(postNagReqDto));

    //verify
    verify(tagRepository, times(1)).findById(tag.getId());
  }

  @DisplayName("잔소리가 content가 존재하고 해시태그 ID가 존재하면 잔소리 생성에 성공한다.")
  @Test
  void Given_ValidNag_When_SaveNag_Then_Success() {
    //given
    postNagReqDto = new PostNagReqDto("공부 안할래?", tag.getId());
    given(tagRepository.findById(tag.getId())).willReturn(Optional.of(tag));
    given(securityUtil.getCurrentMemberByToken()).willReturn(member);

    //when
    when(previewUtil.convertNagToPreview(postNagReqDto.getContent())).thenReturn("ㄱㅂ ㅇㅎㄹ?");
    when(nagRepository.save(any(Nag.class))).thenReturn(Nag.builder().id(1L).tag(tag).build());

    //then
    PostNagResDto postNagResDto = nagService.createNag(postNagReqDto);
    assertThat(postNagResDto.getNagId()).isEqualTo(1L);

    //verify
    verify(tagRepository, times(1)).findById(tag.getId());
    verify(nagRepository, times(1)).save(any(Nag.class));
  }

  @DisplayName("잔소리 ID가 존재하지 않다면 잔소리 좋아요 생성 또는 삭제에 실패한다.")
  @Test
  void Given_NotExistNag_When_RegisterNagLike_Then_Fail() {
    //given
    given(nagRepository.findById(nag.getId())).willReturn(Optional.empty());

    //then
    assertThrows(NagNotFoundException.class,
        () -> nagService.toggleNagLike(nag.getId()));

    //verify
    verify(nagRepository, times(1)).findById(nag.getId());
  }


  @DisplayName("잔소리 좋아요 취소에 성공한다.")
  @Test
  void Given_Valid_MemberIdWithNagId_When_NagLikeDelete_Then_Success() {
    //given
    given(nagRepository.findById(nag.getId())).willReturn(Optional.of(nag));
    given(securityUtil.getCurrentMemberByToken()).willReturn(member);
    given(nagLikeRepository.findNagLikeByNagAndMember(nag, member)).willReturn(
        Optional.of(nagLike));

    //then
    nagService.toggleNagLike(nag.getId());

    //verify
    verify(securityUtil, times(1)).getCurrentMemberByToken();
    verify(nagRepository, times(1)).findById(nag.getId());
    verify(nagLikeRepository, times(1)).findNagLikeByNagAndMember(nag, member);
  }

  @DisplayName("잔소리 좋아요 생성에 성공한다.")
  @Test
  void Given_Valid_MemberIdWithNagId_When_CreateNagLike_Then_Success() {
    //given
    given(nagRepository.findById(nag.getId())).willReturn(Optional.of(nag));
    given(securityUtil.getCurrentMemberByToken()).willReturn(member);
    given(nagLikeRepository.findNagLikeByNagAndMember(nag, member)).willReturn(
        Optional.empty());

    //then
    nagService.toggleNagLike(nag.getId());

    //verify
    verify(securityUtil, times(1)).getCurrentMemberByToken();
    verify(nagRepository, times(1)).findById(nag.getId());
    verify(nagLikeRepository, times(1)).findNagLikeByNagAndMember(nag, member);
  }

  @DisplayName("마이페이지에서 멤버가 작성한 잔소리들이 존재하지 않아도 빈 LIST 조회에 성공한다.")
  @Test
  void Given_Valid_MemberId_When_GetEmptyNagsListByMemberId_Then_Success() {
    //given
    given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

    //when
    GetNagOfProfilePageResDto result = nagService.getAllNagsByMember();

    //then
    assertThat(result.getNags()).isEmpty();

    //verify
    verify(memberRepository, times(1)).findById(member.getId());
  }

  @DisplayName("마이 페이지에서 멤버가 작성한 잔소리들이 존재한다면 LIST 조회에 성공한다.")
  @Test
  void Given_Valid_MemberId_When_GetNagsListByMemberId_Then_Success() {
    //given
    Nag nag = Nag.builder().content("test").likeCount(1).todos(new HashSet<>()).build();
    Tag tag = Tag.builder().name("test").build();
    given(securityUtil.getCurrentMemberByToken()).willReturn(member);

    //when
    GetNagOfProfilePageResDto result = nagService.getAllNagsByMember();

    //then

    //verify
    verify(securityUtil, times(1)).getCurrentMemberByToken();
  }

  @DisplayName("메인 페이지에서 보여줄 랜덤 잔소리5개 또는 5개 이하를 가져오는데 성공한다.")
  @ParameterizedTest
  @MethodSource("generateNags")
  void Given_Nags_When_GetRandomNagsOfMainPage_Then_Success(List<Nag> nags) {
    //given
    given(nagRandomGenerator.getRandomNagsOfMainPage()).willReturn(nags);

    //when
    GetNagOfMainPageResDto getNagOfMainPageResDto = nagService.getRandomNagsOfMainPage();

    //then
    assertThat(getNagOfMainPageResDto.getNags().size())
        .isLessThanOrEqualTo(5);

    //verify
    verify(nagRandomGenerator, times(1)).getRandomNagsOfMainPage();
  }

  @DisplayName("멤버와 잔소리가 존재하고 멤버의 티켓이 1개이상이라면 초성 풀기에 성공한다.")
  @Test
  void Given_Valid_MemberWithNag_When_UnlockNagWIthTicket_Then_Success() {
    //given
    given(securityUtil.getCurrentMemberByToken()).willReturn(member);
    given(nagRepository.findById(nag.getId())).willReturn(Optional.of(nag));
    given(nagUnlockRepository.existsByNagAndMember(nag, member)).willReturn(Boolean.FALSE);

    //when
    NagDto nagDto = nagService.unlockNagPreviewByMemberTicket(nag.getId());

    //then
    assertThat(nagDto.getContent()).isEqualTo(nag.getContent());

    //verify
    verify(securityUtil, times(1)).getCurrentMemberByToken();
    verify(nagRepository, times(1)).findById(nag.getId());
    verify(nagUnlockRepository, times(1)).existsByNagAndMember(nag, member);
  }

  @DisplayName("잔소리함의 통계 조회에 성공한다.")
  @Test
  void Given_Any_When_GetNagBoxStatistics_Then_Success() {
    //given
    given(memberRepository.count()).willReturn(20L);
    given(todoRepository.countTodosByFinishedIsTrue()).willReturn(30L);
    given(nagRepository.count()).willReturn(50L);

    //when
    GetNagBoxStatisticsResDto getNagBoxStatisticsResDto = nagService.getNagBoxStatisticsResDto();

    //then
    assertThat(getNagBoxStatisticsResDto.getTotalMemberCount()).isEqualTo(20L);
    assertThat(getNagBoxStatisticsResDto.getTotalDoneTodoCount()).isEqualTo(30L);
    assertThat(getNagBoxStatisticsResDto.getTotalNagsCount()).isEqualTo(50L);

    //verify
    verify(memberRepository, times(1)).count();
    verify(todoRepository, times(1)).countTodosByFinishedIsTrue();
    verify(nagRepository, times(1)).count();
  }


  static Stream<List<Nag>> generateNags() {
    return Stream.of(
        Collections.emptyList(),
        List.of(new Nag(), new Nag()),
        List.of(new Nag(), new Nag(), new Nag(), new Nag(), new Nag())
    );
  }
}