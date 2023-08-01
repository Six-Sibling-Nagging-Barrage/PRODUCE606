package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.dto.GetMyProfileResDto;
import com.a606.jansori.domain.member.dto.GetUserProfileResDto;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
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
  public GetMyProfileResDto getMyProfile(Long memberId) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException());

    return GetMyProfileResDto.from(member);
  }

    @Transactional
  public PatchMemberInfoResDto UpdateMemberInfo(Long memberId,
        PatchMemberInfoReqDto patchMemberInfoReqDto){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

    member.update(patchMemberInfoReqDto.getNickname(), patchMemberInfoReqDto.getBio(),
        patchMemberInfoReqDto.getImageUrl(), new MemberRole().);

    List<Long> TagList = patchMemberInfoReqDto.getTags();

    if(TagList != null){
      for(Long tagId : TagList){
        Tag tag = tagRepository.findTagById(tagId).
            orElseThrow(TagNotFoundException::new);
        TagFollow.builder()
            .member(member)
            .tag(tag)
            .build();
      }
    }

    return PatchMemberInfoResDto.builder().member(member).build();

  }

}
