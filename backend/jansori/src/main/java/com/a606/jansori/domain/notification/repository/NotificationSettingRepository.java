package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationSetting;
import com.a606.jansori.domain.notification.domain.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {

  NotificationSetting findByNotificationTypeAndMember(NotificationType notificationType, Member member);
}
