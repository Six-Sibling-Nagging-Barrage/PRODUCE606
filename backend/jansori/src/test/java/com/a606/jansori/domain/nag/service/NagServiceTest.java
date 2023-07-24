package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.exception.NagNotWriteException;
import com.a606.jansori.domain.nag.repository.NagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    @Mock
    private NagRepository nagRepository;
    @InjectMocks
    private NagService nagService;

    private PostNagReqDto postNagReqDto;
    private Long memberId;

    @BeforeEach
    public void createPostNag() {
        postNagReqDto = new PostNagReqDto("공부 안 할래?", new ArrayList<>());
        memberId = 1L;
    }

    @DisplayName("잔소리가 해시태그를 1개를 가지고 있지 않다면 잔소리 생성에 실패한다.")
    @Test
    void Given_EmptyHashTag_When_SaveNag_Then_Fail() {
        assertThrows(NagNotWriteException.class, () -> nagService.createNag(memberId, postNagReqDto));
    }
}