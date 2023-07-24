package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.exception.NagNotWriteException;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    @Mock
    private NagRepository nagRepository;
    @Mock
    private TagRepository tagRepository;
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
        postNagReqDto = new PostNagReqDto("공부 안 할래?", tag);
    }

    @DisplayName("잔소리 해시태그가 존재하지 않는 ID 라면 잔소리 생성에 실패한다.")
    @Test
    void Given_NotExistHashTag_When_SaveNag_Then_Fail() {
        //given
        postNagReqDto = new PostNagReqDto("공부 안 할래?", tag);
        given(tagRepository.existsById(tag.getId())).willReturn(Boolean.FALSE);

        //when with then
        assertThrows(NagNotWriteException.class, () -> nagService.createNag(memberId, postNagReqDto));

        //verify
        verify(tagRepository, times(1)).existsById(tag.getId());
    }
}