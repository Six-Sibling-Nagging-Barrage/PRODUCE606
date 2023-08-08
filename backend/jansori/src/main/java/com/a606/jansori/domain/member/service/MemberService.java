package com.a606.jansori.domain.member.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.MemberRole;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameReqDto;
import com.a606.jansori.domain.member.dto.GetDuplicateNicknameResDto;
import com.a606.jansori.domain.member.dto.GetMemberProfileResDto;
import com.a606.jansori.domain.member.dto.PatchMemberNotificationSettingReqDto;
import com.a606.jansori.domain.member.dto.PatchMemberNotificationSettingResDto;
import com.a606.jansori.domain.member.dto.PostMemberInfoReqDto;
import com.a606.jansori.domain.member.dto.PostMemberInfoResDto;
import com.a606.jansori.domain.member.exception.DuplicatedNicknameException;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagFollowRepository;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final TagRepository tagRepository;
  private final TagFollowRepository tagFollowRepository;
  private final SecurityUtil securityUtil;
  private final NotificationSettingRepository notificationSettingRepository;

  @Transactional(readOnly = true)
  public GetDuplicateNicknameResDto checkNicknameIsDuplicated(
      GetDuplicateNicknameReqDto getDuplicateNicknameReqDto) {

    Boolean isExist = memberRepository.existsByNickname(getDuplicateNicknameReqDto.getNickname());
    if (isExist) {
      throw new DuplicatedNicknameException();
    }

    return GetDuplicateNicknameResDto.from(false);
  }

  @Transactional(readOnly = true)
  public GetMemberProfileResDto getMemberProfile(Long memberId) {

    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new MemberNotFoundException());

    return GetMemberProfileResDto.from(member);
  }

  @Transactional
  public PostMemberInfoResDto updateMemberInfo(PostMemberInfoReqDto postMemberInfoReqDto,
      String imageName) {
    Member member = securityUtil.getCurrentMemberByToken();

    member.update(postMemberInfoReqDto.getNickname(), postMemberInfoReqDto.getBio(),
        imageName, MemberRole.USER);

    List<Long> tags = postMemberInfoReqDto.getTags();

    if (tags != null) {
      for (Long tagId : tags) {
        Tag tag = tagRepository.findTagById(tagId).orElseThrow(TagNotFoundException::new);

        followTags(member, tag);
      }
    }

    return PostMemberInfoResDto.of(member, tags);

  }

  private void followTags(Member member, Tag tag) {

    if (tagFollowRepository.findTagFollowByTagAndMember(tag, member).isEmpty()) {

      tagFollowRepository.save(TagFollow.builder()
          .member(member)
          .tag(tag)
          .build());

    }
  }

  @Transactional
  public PatchMemberNotificationSettingResDto setNotificationSettings(
      PatchMemberNotificationSettingReqDto patchNotificationSettingReqDto) {

//    Member member = securityUtil.getCurrentMemberByToken();

//    log.info(String.valueOf(member.getId()));

    Map<Long, Boolean> isNotificationActivated =
        patchNotificationSettingReqDto.getNotificationSettings();

    List<NotificationSetting> notificationSettings =
        notificationSettingRepository.findAllByMemberId(patchNotificationSettingReqDto.getMemberId());

    notificationSettings.
        forEach(notificationSetting -> notificationSetting.getActivated());

//    notificationSettingRepository.saveAll(notificationSettings);

    return PatchMemberNotificationSettingResDto.from(isNotificationActivated);

  }
}