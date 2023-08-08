package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
  List<NotificationSetting> findAllByMember(Member member);

  List<NotificationSetting> findAllByMemberId(Long id);

  Optional<NotificationSetting> findNotificationSettingByMemberAndNotificationTypeId(
      Member member, Long notificationType);
}
