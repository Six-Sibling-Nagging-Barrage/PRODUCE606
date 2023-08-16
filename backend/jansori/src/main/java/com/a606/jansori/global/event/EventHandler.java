package com.a606.jansori.global.event;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.service.ReadyMadeNagService;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.exception.NotificationSettingNotFoundException;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import com.a606.jansori.domain.notification.service.NotificationService;
import com.a606.jansori.domain.persona.domain.TodoPersona;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.infra.message.domain.FcmToken;
import com.a606.jansori.infra.message.repository.FcmTokenRepository;
import com.a606.jansori.infra.message.service.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventHandler {

  private final NotificationService notificationService;

  private final ReadyMadeNagService readyMadeNagService;
  private final FcmService fcmService;

  private final FcmTokenRepository fcmTokenRepository;
  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  @TransactionalEventListener(classes = {
      NotificationCreateEvent.class}, phase = TransactionPhase.AFTER_COMMIT)
  public void pushNotification(final NotificationCreateEvent notificationCreateEvent) {

    Member receiver = notificationCreateEvent.getNotification().getReceiver();
    List<FcmToken> fcmTokens = fcmTokenRepository.findAllByMember(receiver);

    for (FcmToken fcmToken : fcmTokens) {
      String targetToken = fcmToken.getFcmToken();
      fcmService.sendWebPushMessage(
          targetToken, notificationCreateEvent.getTitle(), notificationCreateEvent.getBody());
    }
  }

  @EventListener(classes = {PersonaReactionEvent.class})
  public void createNotificationByWritePersonaNagOnTodo(
      final PersonaReactionEvent personaReactionEvent) {

    TodoPersona todoPersona = personaReactionEvent.getTodoPersona();

    Todo todo = todoPersona.getTodo();

    Member receiver = todoPersona.getTodo().getMember();
    NotificationType notificationType =
        notificationTypeRepository.findByTypeName(NotificationTypeName.MY_TODO_GOT_NEW_NAG);

    if (isNotificationSettingOn(notificationType, receiver)) {
      notificationService.saveNotification(notificationType, todo, todoPersona.getPersona());
    }
  }

  @EventListener(classes = {NagDeliveryEvent.class})
  public void createNotificationByWriteMemberNagOnTodo(final NagDeliveryEvent nagDeliveryEvent) {

    Todo todo = nagDeliveryEvent.getTodo();
    Member receiverWhoNagged = todo.getNag().getMember();
    Member receiverWhoPostedTodo = todo.getNag().getMember();

    NotificationType MY_TODO_GOT_NEW_NAG =
        notificationTypeRepository.findByTypeName(NotificationTypeName.MY_TODO_GOT_NEW_NAG);

    NotificationType MY_NAG_DELIVERED =
        notificationTypeRepository.findByTypeName(NotificationTypeName.MY_NAG_DELIVERED);

    if (isNotificationSettingOn(MY_NAG_DELIVERED, receiverWhoNagged)) {
      notificationService.saveNotification(MY_NAG_DELIVERED, todo.getContent(), receiverWhoNagged);
    }

    if (isNotificationSettingOn(MY_TODO_GOT_NEW_NAG, nagDeliveryEvent.getTodo().getMember())) {
      notificationService.saveNotification(MY_TODO_GOT_NEW_NAG, todo, receiverWhoPostedTodo);
    }
  }

  @EventListener(classes = {TodoAccomplishmentEvent.class})
  public void createNotificationByTodoComplete(
      final TodoAccomplishmentEvent todoAccomplishmentEvent) {

    Todo todo = todoAccomplishmentEvent.getTodo();

    if (todo.getNag() == null) {
      return;
    }

    Member receiver = todo.getNag().getMember();

    NotificationType notificationType =
        notificationTypeRepository.findByTypeName(
            NotificationTypeName.TODO_WITH_YOUR_NAG_ACCOMPLISHED);

    if (isNotificationSettingOn(notificationType, receiver)) {
      notificationService.saveNotification(notificationType, todo.getContent(), receiver);
    }
  }

  @TransactionalEventListener(value = NagWithReadyMadeTagEvent.class, phase = TransactionPhase.AFTER_COMMIT)
  public void handleNagWithReadyMadeTagEvent(NagWithReadyMadeTagEvent event) {

    readyMadeNagService.deliverNagToWaitingTodoQueue(event.getNag());

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
