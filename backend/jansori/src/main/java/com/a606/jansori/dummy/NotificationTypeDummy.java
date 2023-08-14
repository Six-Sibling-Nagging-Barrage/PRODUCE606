package com.a606.jansori.dummy;

import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.domain.NotificationTypeName;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NotificationTypeDummy {

  private final NotificationTypeRepository notificationTypeRepository;

  public List<NotificationType> createNotificationType(){
    List<NotificationType> notificationTypes = new ArrayList<>();
    NotificationTypeName[] notificationTypeNames = NotificationTypeName.values();

    for (int i = 0; i < notificationTypeNames.length; i++) {
      NotificationType notificationType = NotificationType.builder()
          .typeName(notificationTypeNames[i])
          .build();

      notificationTypes.add(notificationType);
    }

    return notificationTypeRepository.saveAll(notificationTypes);
  }


}
