package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;

@Getter
@Entity(name = "notification_box")
public class NotificationBox {

  @Id
  @Column(name = "notification_box_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private LocalDateTime readAt;

  @OneToOne
  @JoinColumn(name = "member_id")
  private Member member;
}