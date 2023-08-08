package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.dto.NotificationDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsReqDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsResDto;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.GetTodoFeedResDto;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
  public PatchNotificationsResDto patchNotifications(
      PatchNotificationsReqDto patchNotificationsReqDto) {

//    Member member = securityUtil.getCurrentMemberByToken();
    Member member = memberRepository.findById(10L).orElseThrow(MemberNotFoundException::new);

//    NotificationBox notificationBox = notificationBoxRepository.findByMember(member);
    NotificationBox notificationBox = notificationBoxRepository.findByMember(member);
    LocalDateTime lastReadAt = notificationBox.getReadAt();

    Long cursor = patchNotificationsReqDto.getCursor();
    Integer size = patchNotificationsReqDto.getSize();

    Slice<Notification> pagedNotifications =
        notificationRepository.findByReceiverOrderByCreatedAtDesc(member,
            PageRequest.of(0, size));

    if (cursor == null) {
      notificationBox.updateReadAt(LocalDateTime.now(clock));
    }

    return patchNotificationsResDtoFrom(size, lastReadAt, pagedNotifications);
  }
  private PatchNotificationsResDto patchNotificationsResDtoFrom(Integer size, LocalDateTime lastReadAt,
      Slice<Notification> pagedNotifications) {

    Boolean hasNext = pagedNotifications.hasNext();
    Long nextCursor = hasNext ? pagedNotifications.getContent().get(size - 1).getId() : null;
    List<Notification> notifications = pagedNotifications.getContent();

    return PatchNotificationsResDto.builder()
        .notifications(notifications.stream()
            .map(NotificationDto::from)
            .collect(Collectors.toList()))
        .lastReadAt(lastReadAt)
        .nextCursor(nextCursor)
        .hasNext(hasNext)
        .build();
  }
}