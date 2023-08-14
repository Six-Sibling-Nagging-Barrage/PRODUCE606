package com.a606.jansori.dummy;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import com.a606.jansori.domain.notification.repository.NotificationBoxRepository;
import com.a606.jansori.domain.notification.repository.NotificationSettingRepository;
import com.a606.jansori.domain.notification.repository.NotificationTypeRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class NotificationBoxDummy {
  private final NotificationBoxRepository notificationBoxRepository;
  private final Clock clock;

  public List<NotificationBox> createNotificationBox(List<Member> members) {

    List<NotificationBox> boxes = new ArrayList<>();
    for (int i = 0; i < members.size(); i++) {
      NotificationBox notificationBox = NotificationBox.builder()
          .readAt(LocalDateTime.now(clock))
          .modifiedAt(LocalDateTime.now(clock))
          .member(members.get(i))
          .build();

      boxes.add(notificationBox);
    }

    return notificationBoxRepository.saveAll(boxes);
  }

}
