package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.event.NagLikeEvent;
import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.dto.NotificationDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsReqDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsResDto;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationRepository;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.event.PostTodoEvent;
import com.a606.jansori.domain.todo.event.TodoAccomplishmentEvent;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationBoxRepository notificationBoxRepository;
  private final NotificationTypeRepository notificationTypeRepository;
  private final NotificationSettingRepository notificationSettingRepository;
  private final SecurityUtil securityUtil;

  private final Clock clock;

  @Transactional
  public PatchNotificationsResDto patchNotifications(
      PatchNotificationsReqDto patchNotificationsReqDto) {

    Member member = securityUtil.getCurrentMemberByToken();

    NotificationBox notificationBox = notificationBoxRepository.findByMember(member);
    LocalDateTime readAt = notificationBox.getReadAt();

    Long cursor = patchNotificationsReqDto.getCursor();
    Integer size = patchNotificationsReqDto.getSize();

    Slice<Notification> pagedNotifications = getPagedNotifications(member, cursor, size);

    // 알림함의 첫 페이지의 알림을 조회하는 경우, 조회시간을 업데이트한다.
    if (cursor == null && !pagedNotifications.isEmpty()) {
      notificationBox.updateReadAt(LocalDateTime.now(clock));
    }

    return patchNotificationsResDtoFrom(size, readAt, pagedNotifications);
  }

  @EventListener(classes = {NagLikeEvent.class})
  public void createNotificationByNagLike(final NagLikeEvent nagLikeEvent){

    Member member = nagLikeEvent.getMember();
    Nag nag = nagLikeEvent.getNag();
    NotificationType notificationType = nagLikeEvent.getNotificationType();

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(member.getNickname()+"님이 "+nag.getMember().getNickname()+"님의 잔소리를 좋아합니다.")
        .receiver(nag.getMember())
        .createdAt(LocalDateTime.now(clock))
        .build();

    notificationRepository.save(notification);
  }

  @EventListener(classes = {PostTodoEvent.class})
  public void createNotificationByWriteMemberNagOnTodo(final PostTodoEvent postTodoEvent){

  }

//  @EventListener(classes = {WritePersonaNagOnTodoEvent.class})
//  public void createNotificationByWritePersonaNagOnTodo(final WritePersonaNagOnTodoEvent writePersonaNagOnTodoEvent){
//
//  }

  @EventListener(classes = {TodoAccomplishmentEvent.class})
  public void createNotificationByTodoComplete(final TodoAccomplishmentEvent todoAccomplishmentEvent){
    Todo todo = todoAccomplishmentEvent.getTodo();
    NotificationType notificationType = todoAccomplishmentEvent.getNotificationType();

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(todo.getNag().getMember().getNickname() + "님의 잔소리가 남겨진 "
            + todo.getMember().getNickname()+"님의 Todo \""
            + todo.getContent() + "\"가 완료되었습니다.")
        .receiver(todo.getNag().getMember())
        .createdAt(LocalDateTime.now(clock))
        .build();

    notificationRepository.save(notification);
  }

  private Slice<Notification> getPagedNotifications(Member member, Long cursor, Integer size) {

    Pageable pageable = PageRequest.of(0, size);

    return cursor != null ? notificationRepository
        .findNotificationByReceiverAndLessThanCursorOrderByIdDesc(
            member, cursor, pageable)
        : notificationRepository.findByReceiverOrderByIdDesc(member, pageable);
  }

  private PatchNotificationsResDto patchNotificationsResDtoFrom(Integer size,
      LocalDateTime lastReadAt,
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