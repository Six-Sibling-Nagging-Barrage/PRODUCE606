package com.a606.jansori.domain.notification.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
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
import com.a606.jansori.domain.persona.domain.PersonaReaction;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.event.NagGenerateEvent;
import com.a606.jansori.domain.todo.event.PostPersonaReactionEvent;
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

  @EventListener(classes = {PostTodoEvent.class})
  public void createNotificationByWriteMemberNagOnTodo(final PostTodoEvent postTodoEvent) {
    Todo todo = postTodoEvent.getTodo();
    Nag nag = postTodoEvent.getTodo().getNag();
    NotificationType notificationType = postTodoEvent.getNotificationType();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(todo.getMember());

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(nag.getMember().getNickname() + "님이 "
            + "\"" + todo.getContent() + "\"에 잔소리를 남겼습니다.")
        .talkerId(nag.getMember().getId())
        .talkerType(TalkerType.MEMBER)
        .receiver(todo.getMember())
        .build();

    notificationRepository.save(notification);
    notificationBox.updateModifiedAt(LocalDateTime.now(clock));

  }

  @EventListener(classes = {PostPersonaReactionEvent.class})
  public void createNotificationByWritePersonaNagOnTodo(
      final PostPersonaReactionEvent postPersonaReactionEvent) {

    TodoPersona todoPersona = postPersonaReactionEvent.getTodoPersona();
    NotificationType notificationType = postPersonaReactionEvent.getNotificationType();
    NotificationBox notificationBox =
        notificationBoxRepository.findByMember(todoPersona.getTodo().getMember());

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(todoPersona.getPersona().getName() + "가 \""
            + todoPersona.getTodo().getContent() + "\"에 잔소리를 남겼습니다.")
        .talkerId(todoPersona.getPersona().getId())
        .talkerType(TalkerType.PERSONA)
        .receiver(todoPersona.getTodo().getMember())
        .build();

    notificationRepository.save(notification);
    notificationBox.updateModifiedAt(LocalDateTime.now(clock));
  }

  @EventListener(classes = {NagGenerateEvent.class})
  public void createNotificationByNagGenerate(final NagGenerateEvent nagGenerateEvent) {
    Todo todo = nagGenerateEvent.getTodo();
    NotificationType notificationType = nagGenerateEvent.getNotificationType();
    NotificationBox notificationBox =
        notificationBoxRepository.findByMember(todo.getNag().getMember());

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(todo.getMember().getNickname() + "님의 \""
            + todo.getContent() + "\"에 잔소리를 남겼습니다.")
        .receiver(todo.getNag().getMember())
        .build();

    notificationRepository.save(notification);
    notificationBox.updateModifiedAt(LocalDateTime.now(clock));
  }

  @EventListener(classes = {NagLikeEvent.class})
  public void createNotificationByNagLike(final NagLikeEvent nagLikeEvent) {

    Member member = nagLikeEvent.getMember();
    Nag nag = nagLikeEvent.getNag();
    NotificationType notificationType = nagLikeEvent.getNotificationType();
    NotificationBox notificationBox = notificationBoxRepository.findByMember(nag.getMember());

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(member.getNickname() + "님이 " + nag.getMember().getNickname() + "님의 잔소리를 좋아합니다.")
        .receiver(nag.getMember())
        .createdAt(LocalDateTime.now(clock))
        .build();

    notificationRepository.save(notification);
    notificationBox.updateModifiedAt(LocalDateTime.now(clock));
  }

  @EventListener(classes = {TodoAccomplishmentEvent.class})
  public void createNotificationByTodoComplete(
      final TodoAccomplishmentEvent todoAccomplishmentEvent) {
    Todo todo = todoAccomplishmentEvent.getTodo();
    NotificationType notificationType = todoAccomplishmentEvent.getNotificationType();
    NotificationBox notificationBox =
        notificationBoxRepository.findByMember(todo.getNag().getMember());

    final Notification notification = Notification.builder()
        .notificationType(notificationType)
        .content(todo.getNag().getMember().getNickname() + "님의 잔소리가 남겨진 "
            + todo.getMember().getNickname() + "님의 Todo \""
            + todo.getContent() + "\"가 완료되었습니다.")
        .receiver(todo.getNag().getMember())
        .createdAt(LocalDateTime.now(clock))
        .build();

    notificationRepository.save(notification);
    notificationBox.updateModifiedAt(LocalDateTime.now(clock));
  }

}