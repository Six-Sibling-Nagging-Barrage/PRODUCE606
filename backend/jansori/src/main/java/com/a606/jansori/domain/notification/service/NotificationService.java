package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.dto.PatchNotificationsResDto;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationBoxRepository notificationBoxRepository;
  private final MemberRepository memberRepository;
  private final SecurityUtil securityUtil;

  private final Clock clock;

  @Transactional
  public PatchNotificationsResDto patchNotifications() {

//    Member member = getMemberFromSecurityUtil();
    Member member = memberRepository.findById(1L).orElseThrow(MemberNotFoundException::new);
    NotificationBox notificationBox = notificationBoxRepository.findByMember(member);

    PatchNotificationsResDto patchNotificationsResDto =
        PatchNotificationsResDto
            .from(notificationRepository.findByReceiverOrderByCreatedAtDesc(member),
            notificationBox.getReadAt());

    notificationBox.updateReadAt(LocalDateTime.now(clock));

    return patchNotificationsResDto;
  }

  private Member getMemberFromSecurityUtil() {

    return memberRepository.findById(securityUtil.getSessionMemberId())
        .orElseThrow(MemberNotFoundException::new);
  }
}