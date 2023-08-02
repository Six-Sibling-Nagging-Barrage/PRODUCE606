package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.notification.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

  @Query(value = "SELECT distinct n FROM notification n " +
        "JOIN n.receiver m " +
        "JOIN notification_box b " +
        "JOIN notification_setting s " +
        "JOIN n.notificationType t " +
        "WHERE n.receiver = :memberId " +
        "AND s.activated = true " +
        "AND n.createdAt > b.readAt " +
        "ORDER BY n.createdAt desc")
  List<Notification> findByIdAndCreatedAtAfterReadAt(Long memberId);
}