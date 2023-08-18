package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationBoxRepository extends JpaRepository<NotificationBox, Long> {

  NotificationBox findByMember(Member member);

  @Query("SELECT nb FROM notification_box nb WHERE nb.member = :member AND nb.modifiedAt > nb.readAt")
  Optional<NotificationBox> findUnreadNotificationByMember(@Param("member") Member member);
}
