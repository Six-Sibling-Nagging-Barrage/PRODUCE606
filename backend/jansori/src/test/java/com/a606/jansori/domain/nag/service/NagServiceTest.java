package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.exception.NagNotWriteException;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    @Mock
    private NagRepository nagRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private NagTagRepository nagTagRepository;
    @InjectMocks
    private NagService nagService;

    private PostNagReqDto postNagReqDto;
    private Long memberId;
    private Tag tag;

    @BeforeEach
    public void createPostNag() {
        memberId = 1L;
        tag = Tag.builder()
                .id(1L)
                .name("운동")
                .count(1L)
                .build();
    }

    @DisplayName("잔소리 해시태그가 존재하지 않는 ID 라면 잔소리 생성에 실패한다.")
    @Test
    void Given_NotExistHashTag_When_SaveNag_Then_Fail() {
        //given
        postNagReqDto = new PostNagReqDto("공부 안할래?", tag.getId());
        given(tagRepository.findById(tag.getId())).willReturn(Optional.empty());

        //when with then
        assertThrows(NagNotWriteException.class, () -> nagService.createNag(memberId, postNagReqDto));

        //verify
        verify(tagRepository, times(1)).findById(tag.getId());
    }

    @DisplayName("잔소리가 content가 존재하고 해시태그 ID가 존재하면 잔소리 생성에 성공한다.")
    @Test
    void Given_ValidNag_When_SaveNag_Then_Success() {
        //given
        postNagReqDto = new PostNagReqDto("공부 안할래?", tag.getId());
        given(tagRepository.findById(tag.getId())).willReturn(Optional.of(tag));

        //when
        when(nagRepository.save(any(Nag.class))).thenReturn(null);
        when(nagTagRepository.save(any(NagTag.class))).thenReturn(null);

        //then
        nagService.createNag(memberId, postNagReqDto);

        //verify
        verify(tagRepository, times(1)).findById(tag.getId());
        verify(nagRepository, times(1)).save(any(Nag.class));
        verify(nagTagRepository, times(1)).save(any(NagTag.class));
    }
}