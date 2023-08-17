package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.notification.domain.Notification;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.dto.GetNotificationBoxCheckResDto;
import com.a606.jansori.domain.notification.dto.NotificationDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsReqDto;
import com.a606.jansori.domain.notification.dto.PatchNotificationsResDto;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.global.event.NotificationCreateEvent;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationRepository;
import com.a606.jansori.domain.notification.util.MessageUtil;
import com.a606.jansori.domain.persona.domain.Persona;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
  private final ApplicationEventPublisher publisher;
  private final SecurityUtil securityUtil;

  private final MessageUtil messageUtil;
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

  @Transactional
  public Notification saveNotification(NotificationType notificationType, Todo todo) {

    Member receiver = todo.getNag().getMember();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(receiver);

    Notification notification = notificationRepository.save(Notification.builder()
        .notificationType(notificationType)
        .content(messageUtil.getNotificationContent(notificationType.getTypeName(),
                todo.getMember().getNickname(),
                todo.getContent()
        ))
        .receiver(receiver)
        .build());

    notificationBox.updateModifiedAt(LocalDateTime.now(clock));

    publisher.publishEvent(
        new NotificationCreateEvent(notification,
            messageUtil.getMessageTitle(),
            messageUtil.getMessageBody(notificationType.getTypeName()))
    );

    return notification;
  }

  @Transactional
  public Notification saveNotification(NotificationType notificationType, Todo todo,
      Member talker) {

    Member receiver = todo.getMember();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(receiver);

    Notification notification = notificationRepository.save(Notification.builder()
        .notificationType(notificationType)
        .content(messageUtil.getNotificationContent(notificationType.getTypeName(),
            talker.getNickname(),
            todo.getContent()
        ))
        .talkerId(talker.getId())
        .talkerType(TalkerType.MEMBER)
        .receiver(receiver)
        .build());

    notificationBox.updateModifiedAt(LocalDateTime.now(clock));

    publisher.publishEvent(
        new NotificationCreateEvent(notification,
            messageUtil.getMessageTitle(),
            messageUtil.getMessageBody(notificationType.getTypeName()))
    );

    return notification;
  }

  @Transactional
  public Notification saveNotification(NotificationType notificationType, TodoPersona todoPersona) {

    Member receiver = todoPersona.getTodo().getMember();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(receiver);

    Notification notification = notificationRepository.save(Notification.builder()
        .notificationType(notificationType)
        .content(messageUtil.getNotificationContent(notificationType.getTypeName(),
            todoPersona.getPersona().getName(),
            todoPersona.getTodo().getContent()
        ))
        .talkerId(todoPersona.getPersona().getId())
        .talkerType(TalkerType.PERSONA)
        .receiver(receiver)
        .build());

    notificationBox.updateModifiedAt(LocalDateTime.now(clock));

    publisher.publishEvent(
        new NotificationCreateEvent(notification,
            messageUtil.getMessageTitle(),
            messageUtil.getMessageBody(notificationType.getTypeName()))
    );

    return notification;
  }

  @Transactional
  public Notification saveNotification(NotificationType notificationType, Member liker,
                                       Nag nag) {

    Member receiver = nag.getMember();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(receiver);

    Notification notification = notificationRepository.save(Notification.builder()
            .notificationType(notificationType)
            .content(messageUtil.getNotificationContent(notificationType.getTypeName(),
                    liker.getNickname(),
                    receiver.getNickname()
            ))
            .receiver(receiver)
            .build());

    notificationBox.updateModifiedAt(LocalDateTime.now(clock));

    publisher.publishEvent(
            new NotificationCreateEvent(notification,
                    messageUtil.getMessageTitle(),
                    messageUtil.getMessageBody(notificationType.getTypeName()))
    );

    return notification;
  }

  public GetNotificationBoxCheckResDto checkNotificationBox(){
    Member member = securityUtil.getCurrentMemberByToken();

    boolean isUnreadNotificationLeft =
        notificationBoxRepository.findUnreadNotificationByMember(member).isPresent();

    return GetNotificationBoxCheckResDto.builder()
        .isUnreadNotificationLeft(isUnreadNotificationLeft)
        .build();

  }

}