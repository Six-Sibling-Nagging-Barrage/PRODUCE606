package com.a606.jansori.domain.todo.event.handler;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.todo.event.PostTodoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostTodoEventListener {

  private final NotificationService notificationService;

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  @EventListener(classes = {PostTodoEvent.class})
  public void createNotificationByWriteMemberNagOnTodo(final PostTodoEvent postTodoEvent) {

    NotificationType notificationType =
            notificationTypeRepository.findByTypeName(NotificationTypeName.MY_TODO_GOT_NEW_NAG);

    if (isNotificationSettingOn(notificationType, postTodoEvent.getTodo().getMember())) {
      notificationService.saveNotification(notificationType, postTodoEvent.getTodo(),
          postTodoEvent.getTodo().getNag().getMember());
    }
  }

  private Boolean isNotificationSettingOn(NotificationType notificationType, Member member) {

    NotificationSetting notificationSetting =
            notificationSettingRepository.findByNotificationTypeAndMember(notificationType, member);

    if (notificationSetting == null) {
      throw new NotificationSettingNotFoundException();
    }

    return notificationSetting.getActivated();
  }

}
