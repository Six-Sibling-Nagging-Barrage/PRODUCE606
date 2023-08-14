package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NotificationSettingDummy {

  private final NotificationSettingRepository notificationSettingRepository;
  private final NotificationTypeRepository notificationTypeRepository;

  public List<NotificationSetting> createNotificationSettings(List<Member> members) {

    List<NotificationType> types = notificationTypeRepository.findAll();
    List<NotificationSetting> settings = new ArrayList<>();
    for (int i = 0; i < members.size(); i++) {
      for(int j = 0; j <= 3; j++){
        NotificationSetting notificationSetting = NotificationSetting.builder()
            .activated(true)
            .member(members.get(i))
            .notificationType(types.get(j))
            .build();

        settings.add(notificationSetting);
      }
    }

    return notificationSettingRepository.saveAll(settings);
  }

}
