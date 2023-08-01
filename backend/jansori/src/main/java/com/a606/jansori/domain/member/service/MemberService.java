package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.dto.*;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final TagFollowRepository tagFollowRepository;
    private final SecurityUtil securityUtil;

    @Transactional(readOnly = true)
    public GetDuplicateNicknameResDto checkNicknameIsAvailable(
            GetDuplicateNicknameReqDto getDuplicateNicknameReqDto) {

        Boolean isExist = memberRepository.existsByNickname(getDuplicateNicknameReqDto.getNickname());
        if (isExist) {
            throw new DuplicatedNicknameException();
        }

        return GetDuplicateNicknameResDto.from(true);
    }

    @Transactional(readOnly = true)
    public GetUserProfileResDto getUserProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        return GetUserProfileResDto.from(member);
    }

    @Transactional(readOnly = true)
    public GetMyProfileResDto getMyProfile() {

        Member member = getMemberFromSecurityUtil();

        return GetMyProfileResDto.from(member);
    }

    private Member getMemberFromSecurityUtil() {

        return memberRepository.findById(securityUtil.getSessionMemberId())
                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public PatchMemberInfoResDto updateMemberInfo(PatchMemberInfoReqDto patchMemberInfoReqDto) {
        Member member = getMemberFromSecurityUtil();

        member.update(patchMemberInfoReqDto.getNickname(), patchMemberInfoReqDto.getBio(),
                patchMemberInfoReqDto.getImageUrl(), MemberRole.USER);

        List<Long> tags = patchMemberInfoReqDto.getTags();

        if (tags != null) {
            for (Long tagId : tags) {
                Tag tag = tagRepository.findTagById(tagId).
                        orElseThrow(TagNotFoundException::new);
                if (tagFollowRepository.findTagFollowByTagAndMember(tag, member).isEmpty()) {
                    tagFollowRepository.save(TagFollow.builder()
                            .member(member)
                            .tag(tag)
                            .build());
                }
            }
        }

        return PatchMemberInfoResDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .bio(member.getBio())
                .imageUrl(member.getImageUrl())
                .tags(tags)
                .build();

    }

}
