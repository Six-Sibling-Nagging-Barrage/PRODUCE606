package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagLike;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.exception.NagNotFoundException;
import com.a606.jansori.domain.nag.repository.NagLikeRepository;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    @Mock
    private NagRepository nagRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private NagTagRepository nagTagRepository;
    @Mock
    private NagLikeRepository nagLikeRepository;
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
                .build();
        tag = Tag.builder()
                .id(1L)
                .name("운동")
                .followCount(1)
                .build();
        nag = Nag.builder()
                .id(1L)
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
                () -> nagService.createNag(member.getId(), postNagReqDto));

        //verify
        verify(tagRepository, times(1)).findById(tag.getId());
    }

    @DisplayName("잔소리가 content가 존재하고 해시태그 ID가 존재하면 잔소리 생성에 성공한다.")
    @Test
    void Given_ValidNag_When_SaveNag_Then_Success() {
        //given
        postNagReqDto = new PostNagReqDto("공부 안할래?", tag.getId());
        given(tagRepository.findById(tag.getId())).willReturn(Optional.of(tag));
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        //when
        when(nagRepository.save(any(Nag.class))).thenReturn(Nag.builder().id(1L).build());
        when(nagTagRepository.save(any(NagTag.class))).thenReturn(null);

        //then
        PostNagResDto postNagResDto = nagService.createNag(member.getId(), postNagReqDto);
        assertThat(postNagResDto.getNagId()).isEqualTo(1L);

        //verify
        verify(tagRepository, times(1)).findById(tag.getId());
        verify(nagRepository, times(1)).save(any(Nag.class));
        verify(nagTagRepository, times(1)).save(any(NagTag.class));
    }

    @DisplayName("잔소리 ID가 존재하지 않다면 잔소리 좋아요 생성 또는 삭제에 실패한다.")
    @Test
    void Given_NotExistNag_When_RegisterNagLike_Then_Fail() {
        //given
        given(nagRepository.findById(nag.getId())).willReturn(Optional.empty());

        //then
        assertThrows(NagNotFoundException.class,
                () -> nagService.toggleNagLike(member.getId(), nag.getId()));

        //verify
        verify(nagRepository, times(1)).findById(nag.getId());
    }


    @DisplayName("잔소리 좋아요 취소에 성공한다.")
    @Test
    void Given_Valid_MemberIdWithNagId_When_NagLikeDelete_Then_Success() {
        //given
        given(nagRepository.findById(nag.getId())).willReturn(Optional.of(nag));
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(nagLikeRepository.findNagLikeByNagAndMember(nag, member)).willReturn(Optional.of(nagLike));

        //then
        nagService.toggleNagLike(member.getId(), nag.getId());

        //verify
        verify(memberRepository, times(1)).findById(member.getId());
        verify(nagRepository, times(1)).findById(nag.getId());
        verify(nagLikeRepository, times(1)).findNagLikeByNagAndMember(nag, member);
    }

    @DisplayName("잔소리 좋아요 생성에 성공한다.")
    @Test
    void Given_Valid_MemberIdWithNagId_When_CreateNagLike_Then_Success() {
        //given
        given(nagRepository.findById(nag.getId())).willReturn(Optional.of(nag));
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));
        given(nagLikeRepository.findNagLikeByNagAndMember(nag, member)).willReturn(Optional.empty());

        //then
        nagService.toggleNagLike(member.getId(), nag.getId());

        //verify
        verify(memberRepository, times(1)).findById(member.getId());
        verify(nagRepository, times(1)).findById(nag.getId());
        verify(nagLikeRepository, times(1)).findNagLikeByNagAndMember(nag, member);
    }

    @DisplayName("멤버가 작성한 잔소리들이 존재하지 않아도 빈 LIST 조회에 성공한다.")
    @Test
    void Given_Valid_MemberId_When_GetEmptyNagsListByMemberId_Then_Success() {
        //given
        List<Nag> nags = List.of();
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        //when
        List<Nag> result = nagService.getAllNagsByMember();

        assertThat(result).isEmpty();
    }
}