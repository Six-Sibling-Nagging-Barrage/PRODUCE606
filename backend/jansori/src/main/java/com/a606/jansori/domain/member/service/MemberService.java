package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.dto.GetMyProfileResDto;
import com.a606.jansori.domain.member.dto.GetUserProfileResDto;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

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

    Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

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
  public PostMemberInfoResDto UpdateMemberInfo(Long memberId, PostMemberInfoReqDto postMemberInfoReqDto){
    Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException());

    member.builder()
            .nickname()


  }
}
