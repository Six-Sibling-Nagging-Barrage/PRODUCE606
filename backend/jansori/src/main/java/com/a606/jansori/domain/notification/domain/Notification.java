package com.a606.jansori.domain.notification.domain;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.domain.TalkerType;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="notification")
public class Notification {

  @Id
  @Column(name = "notification_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "notification_type_id")
//  private NotificationType notificationType;

  @Column
  private String content;

  @Column
  private LocalDateTime createdAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member receiver;

  @Enumerated(EnumType.STRING)
  private TalkerType talkerType;

  @Column
  private Long talkerId;
}
