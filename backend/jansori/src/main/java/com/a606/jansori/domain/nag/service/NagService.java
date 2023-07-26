package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NagService {

    private final NagRepository nagRepository;
    private final TagRepository tagRepository;
    private final NagTagRepository nagTagRepository;
    private final MemberRepository memberRepository;
    private final NagLikeRepository nagLikeRepository;

    @Transactional
    public PostNagResDto createNag(Long memberId, PostNagReqDto postNagReqDto) {
        Tag tag = tagRepository.findById(postNagReqDto.getTagId())
                .orElseThrow(TagNotFoundException::new);

        String preview = KoreanUtil.convertToInitialSound(postNagReqDto.getContent());

        Nag nag = Nag.of(memberId, postNagReqDto, preview);
        NagTag nagTag = NagTag.of(nag, tag);

        nagTagRepository.save(nagTag);
        return PostNagResDto.builder()
                .nagId(nagRepository.save(nag).getId())
                .build();
    }

    @Transactional
    public void toggleNagLike(Long memberId, Long nagId) {
        Nag nag = nagRepository.findById(nagId)
                .orElseThrow(NagNotFoundException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        Optional<NagLike> nagLike = nagLikeRepository.findNagLikeByNagAndMember(nag, member);

        nagLike.ifPresentOrElse(nagLikeRepository::delete,
                () -> nagLikeRepository.save(NagLike.builder().nag(nag).member(member).build()));
    }
}
