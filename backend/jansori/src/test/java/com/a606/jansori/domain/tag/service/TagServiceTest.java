package com.a606.jansori.domain.tag.service;

import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagFollowRepository tagFollowRepository;

    @InjectMocks
    private TagService tagService;

    private Long tagId;
    private Long memberId;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("tag를 찾을 수 없으면 태그 팔로우를 실패한다.")
    @Test
    void Given_Invalid_TagId_When_Following_Tag_Then_Fail() {
        //given
        given(tagRepository.findById(tagId)).willReturn(Optional.empty());

        //then
        assertThrows(TagNotFoundException.class, () -> tagService.followTagByTagWithMember(memberId, tagId));

        //verify
        verify(tagRepository, times(1)).findById(tagId);
    }
}