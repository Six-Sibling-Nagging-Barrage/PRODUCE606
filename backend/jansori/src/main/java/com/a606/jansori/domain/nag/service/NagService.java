package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.exception.NagNotWriteException;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NagService {

    private final NagRepository nagRepository;
    private final TagRepository tagRepository;

    @Transactional
    public void createNag(Long memberId, PostNagReqDto postNagReqDto) {
        if (postNagReqDto.getTags().size() != 1) {
            throw new NagNotWriteException("700", "잔소리는 해시태그를 꼭 한 개를 가지고 있어야 합니다.");
        }
        if (!tagRepository.existsById(postNagReqDto.getTags().get(0).getId())) {
            throw new NagNotWriteException("701", "존재하지 않는 해시태그 ID 입니다.");
        }
    }
}
