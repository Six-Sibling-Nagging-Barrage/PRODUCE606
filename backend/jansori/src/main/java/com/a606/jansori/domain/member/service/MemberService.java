package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.dto.GetMyProfileResDto;
import com.a606.jansori.domain.member.dto.GetUserProfileResDto;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

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
  public PostMemberInfoResDto UpdateMemberInfo(Long memberId, PostMemberInfoReqDto postMemberInfoReqDto){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

    member.builder()
            .nickname()


  }
}
