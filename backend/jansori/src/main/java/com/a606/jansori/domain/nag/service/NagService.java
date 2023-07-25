package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.exception.NagNotWriteException;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.domain.NagTag;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.repository.NagTagRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NagService {

    private final NagRepository nagRepository;
    private final TagRepository tagRepository;
    private final NagTagRepository nagTagRepository;

    @Transactional
    public void createNag(Long memberId, PostNagReqDto postNagReqDto) {

        Tag tag = tagRepository.findById(postNagReqDto.getTagId())
                .orElseThrow(() -> new NagNotWriteException("650", "존재하지 않는 해시태그 ID 입니다."));

        String preview = KoreanUtil.convertToInitialSound(postNagReqDto.getContent());

        Nag nag = Nag.of(memberId, postNagReqDto, preview);
        NagTag nagTag = NagTag.of(nag, tag);

        nagRepository.save(nag);
        nagTagRepository.save(nagTag);
    }
}
