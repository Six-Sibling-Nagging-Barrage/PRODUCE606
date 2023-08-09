package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  Slice<Notification> findByReceiverOrderByCreatedAtDesc(Member member, Pageable pageable);

  @Query("SELECT n FROM notification n "
      + "WHERE n.receiver = :member "
      + "AND n.id < :cursor "
      + "ORDER BY n.createdAt DESC")
  Slice<Notification> findNotificationByReceiverAndLessThanCursorOrderByCreatedAtDesc(Member member,
      Long cursor, Pageable pageable);
}