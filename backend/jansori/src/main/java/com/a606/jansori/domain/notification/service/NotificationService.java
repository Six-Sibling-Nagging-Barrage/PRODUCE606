package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.dto.GetNotificationsResDto;
import com.a606.jansori.domain.notification.repository.NotificationRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final MemberRepository memberRepository;
  private final NotificationRepository notificationRepository;
  private final SecurityUtil securityUtil;

  public GetNotificationsResDto getNotifications(){

    Long memberId = securityUtil.getSessionMemberId();

    List<Notification> notifications = notificationRepository
        .findByIdAndCreatedAtAfterReadAt(memberId);

  }

  private Member getMemberFromSecurityUtil() {

    return memberRepository.findById(securityUtil.getSessionMemberId())
        .orElseThrow(MemberNotFoundException::new);
  }
}
