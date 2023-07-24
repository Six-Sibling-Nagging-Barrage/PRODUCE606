package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.repository.NagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NagServiceTest {

    private PostNagReqDto postNagReqDto;

    @BeforeEach
    public void createPostNag() {
        postNagReqDto = new PostNagReqDto();
    }

    @Mock
    private NagRepository nagRepository;
    @InjectMocks
    private NagService nagService;

    @DisplayName("잔소리 내용을 가지고 있지 않다면 생성에 실패한다.")
    void Given_EmptyHashTag_When_SaveNag_Then_Fail() {

        assertThrows(nagService.createNag(postNagReqDto), () -> new RuntimeException());
    }
}