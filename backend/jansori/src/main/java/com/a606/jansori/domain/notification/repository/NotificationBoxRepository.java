package com.a606.jansori.domain.notification.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.notification.domain.NotificationBox;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationBoxRepository extends JpaRepository<NotificationBox, Long> {

  Optional<NotificationBox> findByMember(Member member);

//  Optional<NotificationBox> find
}
